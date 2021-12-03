package com.teamopendata.mindcareapp.ui.records.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.teamopendata.mindcareapp.common.MindChargeDB;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentAddEditRecordBinding;
import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.ui.records.adapter.RecordsAdapter;
import com.teamopendata.mindcareapp.ui.records.adapter.TaskAdapter;
import com.teamopendata.mindcareapp.common.model.entity.Task;
import com.teamopendata.mindcareapp.common.Utils;
import com.teamopendata.mindcareapp.ui.records.listener.OnAddEditRecordEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEditRecordFragment extends Fragment {
    public enum EventType {
        EVENT_ADD,
        EVENT_EDIT,
        EVENT_DELETE;

        @NonNull
        @Override
        public String toString() {
            return this.name();
        }
    }

    private static final String TAG = "AddEditRecordFragment";
    private FragmentAddEditRecordBinding binding;
    private TaskAdapter taskAdapter;

    private EventType mEventType;

    private Toast toastEvent;

    private Record mNewRecord;
    private final Record mCachedRecord;
    private List<LocalDate> mRegisteredDate = null;

    private OnBackPressedCallback backPressedCallback;

    /**
     * @see HomeRecordsFragment called after being saved record
     */
    private final OnAddEditRecordEventListener mSaveListener;

    public AddEditRecordFragment(EventType itemType, Record record, OnAddEditRecordEventListener listener) {
        mEventType = itemType;
        mCachedRecord = record;
        mSaveListener = listener;

        if (mEventType == EventType.EVENT_EDIT) mNewRecord = record.clone();
        else mNewRecord = new Record();

        Log.d(TAG, "AddEditRecordFragment: " + "eventType-> " + mEventType.toString() + "Record-> " + mNewRecord.toString());
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(() -> mRegisteredDate = MindChargeDB.getInstance(requireContext()).getRecordDao().getAllRecordDate()).start();

        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                performEvent();
                Log.d(TAG, "handleOnBackPressed: 제발");
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_record, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toastEvent = Utils.buildSaveToast(view, requireContext(), getLayoutInflater());

        ArrayList<Task> item = new ArrayList<>();

        if (mEventType == EventType.EVENT_ADD) {
            mNewRecord = new Record("", LocalDate.now());
            binding.btnRecordDelete.setVisibility(View.GONE);
        } else {
            item.addAll(mNewRecord.getTasks());
        }
        taskAdapter = new TaskAdapter(item);
        binding.includeRv.rvRecordTask.setAdapter(taskAdapter);
        binding.includeRv.rvRecordTask.addItemDecoration(new DividerItemDecoration(requireContext(), 1));
        new ItemTouchHelper(itemTouchHelperCallback(view)).attachToRecyclerView(binding.includeRv.rvRecordTask);

        binding.includeRv.btnTaskAdd.setOnClickListener(v -> {
            taskAdapter.addTask(new Task());
            taskAdapter.notifyItemChanged(taskAdapter.getItemCount());
        });

        binding.btnRecordSave.setOnClickListener(v -> performEvent());
        binding.btnRecordDelete.setOnClickListener(v -> {
            mEventType = EventType.EVENT_DELETE;
            performEvent();
        });

        binding.etRecordTitle.setText(mNewRecord.getTitle());
        binding.tvRecordPickDate.setText(Utils.LocalDateToString(mNewRecord.getDate()));

        binding.cvRecordDate.setOnClickListener(v -> showDatePickerDialog());
    }

    @NonNull
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback(@NonNull View view) {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                taskAdapter.swapItem(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                RecyclerView.ViewHolder rv = binding.includeRv.rvRecordTask
                        .findViewHolderForAdapterPosition(viewHolder.getLayoutPosition());
                if (rv != null) rv.itemView.findViewById(R.id.et_contents_task).clearFocus();

                taskAdapter.removeItem(viewHolder.getLayoutPosition());
                Snackbar.make(view, "삭제되었습니다.", Snackbar.LENGTH_SHORT)
                        .setAction("되돌리기", v -> taskAdapter.revertItem())
                        .show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float h = itemView.getBottom() - itemView.getTop();
                    float w = h / 4;
                    Paint paint = new Paint();
                    if (dX < 0) {
                        paint.setColor(Color.parseColor("#90CAF9"));
                        RectF background =
                                new RectF(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        c.drawRect(background, paint);

                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_delete);
                        RectF iconDst =
                                new RectF(itemView.getRight() - 3 * w, itemView.getTop() + w, itemView.getRight() - w, itemView.getBottom() - w);
                        c.drawBitmap(icon, null, iconDst, null);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
    }


    private void addRecord() {
        if (validationData()) {
            newRecord();
            new Thread(() -> {
                Log.d(TAG, "addRecord: " + mNewRecord.toString());

                mNewRecord.setId(RecordsAdapter.getCachedRecordId());
                MindChargeDB.getInstance(requireContext()).getRecordDao().insert(mNewRecord);
                new Handler(Looper.getMainLooper()).post(this::showToastAndFinish);
                mSaveListener.onPerformEvent(mNewRecord);
            }).start();
        }
    }

    private void updateRecord() {
        if (validationData()) {
            newRecord();
            if (!mNewRecord.equals(mCachedRecord)) {
                Toast.makeText(requireContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                new Thread(() -> {
                    Log.d(TAG, "updateRecord: " + mNewRecord.toString());

                    MindChargeDB.getInstance(requireContext()).getRecordDao().update(mNewRecord);
                    mSaveListener.onPerformEvent(mNewRecord);
                }).start();
            }
        }
    }

    private void deleteRecord() {
        Toast.makeText(requireContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
        new Thread(() -> {
            Log.d(TAG, "deleteRecord: " + mNewRecord.toString());

            MindChargeDB.getInstance(requireContext()).getRecordDao().delete(mNewRecord);
            mSaveListener.onPerformEvent(null);
        }).start();
    }

    private void newRecord() {
        mNewRecord.setTitle(binding.etRecordTitle.getText().toString());
        mNewRecord.setDate(Utils.StringToLocalDate(binding.tvRecordPickDate.getText().toString()));
        mNewRecord.setTasks(taskAdapter.removeBlankItem());
        Log.d(TAG, "newTask:" + taskAdapter.getItem());
        Log.d(TAG, "newRecord: " + mNewRecord.toString());
    }


    private boolean validationData() {
        if (binding.tvRecordPickDate.getText().toString().length() == 0) {
            Toast.makeText(requireContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
            binding.tvRecordPickDate.requestFocus();
            return false;
        } else if (binding.etRecordTitle.getText().toString().length() == 0) {
            Toast.makeText(requireContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.etRecordTitle.requestFocus();
            return false;
        } else if (!taskAdapter.validationItem() || !taskAdapter.hasItem()) {
            //todo requestFocus or 입력안한 task는 전부 삭제 후 저장?
            Toast.makeText(requireContext(), "할 일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showToastAndFinish() {
        new CountDownTimer(500, 100) {
            public void onTick(long millisUntilFinished) {
                toastEvent.show();
            }

            public void onFinish() {
                toastEvent.cancel();
                getParentFragmentManager().popBackStack();
            }
        }.start();
    }

    /**
     * DatePickerListener
     */
    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            mNewRecord.setDate(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
            binding.tvRecordPickDate.setText(Utils.LocalDateToString(mNewRecord.getDate()));
        }
    };

    /**
     * DatePickerDialog
     */
    private void showDatePickerDialog() {
        LocalDate date = mNewRecord.getDate();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(mDateSetListener, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        datePickerDialog.setLocale(Locale.KOREA);
        datePickerDialog.setMaxDate(Calendar.getInstance());
        datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
    }

    private void performEvent() {
        if (mEventType == EventType.EVENT_ADD) {
            addRecord();
            return;
        } else if (mEventType == EventType.EVENT_EDIT) {
            updateRecord();
        } else {
            deleteRecord();
        }
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }


    @Override
    public void onStop() {

        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        backPressedCallback.remove();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
    }

}