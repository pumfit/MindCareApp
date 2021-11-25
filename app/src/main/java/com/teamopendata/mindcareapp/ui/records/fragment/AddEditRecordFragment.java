package com.teamopendata.mindcareapp.ui.records.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentAddRecordBinding;
import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.ui.records.adapter.TaskAdapter;
import com.teamopendata.mindcareapp.common.model.entity.Task;
import com.teamopendata.mindcareapp.common.Utils;
import com.teamopendata.mindcareapp.ui.records.listener.OnAddEditRecordSaveListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddEditRecordFragment extends Fragment {
    private static final String TAG = "AddEditRecordFragment";
    private FragmentAddRecordBinding binding;
    private TaskAdapter taskAdapter;

    private Toast toastSave;
    private LocalDate date;


    private OnAddEditRecordSaveListener mListener;

    /**
     * @param listener called after being saved record
     */
    public void setOnAddEditRecordSaveListener(OnAddEditRecordSaveListener listener) {
        mListener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_record, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toastSave = Utils.buildSaveToast(view, requireContext(), getLayoutInflater());

        ArrayList<Task> item = new ArrayList<>();
        taskAdapter = new TaskAdapter(item);
        binding.includeRv.rvRecordTask.setAdapter(taskAdapter);

        binding.includeRv.btnTaskAdd.setOnClickListener(v -> {
            taskAdapter.addTask(new Task());
            taskAdapter.notifyItemChanged(taskAdapter.getItemCount());
        });

        binding.btnRecordSave.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        binding.tvRecordPickDate.setText(getDate());

        binding.tvRecordPickDate.setOnClickListener(v -> showDatePickerDialog());

    }

    private void addRecord() {
        Log.d(TAG, "addRecord: " + newRecord().toString());
        new Thread(() -> {
            MindChargeDB.getInstance(requireContext()).getRecordDao().insert(newRecord());
            //mListener.onRecordSave();
        }).start();

        new CountDownTimer(500, 100 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                toastSave.show();
            }

            public void onFinish() {
                toastSave.cancel();
            }
        }.start();
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
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    private String getDate() {
        date = LocalDate.now();
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
    }

    /**
     * DatePickerListener
     */
    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date = LocalDate.of(year, month, dayOfMonth);
            binding.tvRecordPickDate.setText(String.format(Locale.KOREAN, "%d-%d-%d", year, month + 1, dayOfMonth));
        }
    };

    private void showDatePickerDialog() {
        new DatePickerDialog(requireContext(), mDateSetListener, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()).show();
    }

    private Record newRecord() {
        Record record = new Record(binding.etRecordTitle.getText().toString(),
                Utils.StringToLocalDate(binding.tvRecordPickDate.getText().toString()));
        record.setTasks(taskAdapter.getItem());
        Log.d(TAG, "newTask:" + taskAdapter.getItem());
        Log.d(TAG, "newRecord: " + record.toString());
        return record;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        boolean etFlag = binding.etRecordTitle.getText().toString().equals("");
        boolean taskFlag = taskAdapter.getItem().isEmpty();
        if (!etFlag && !taskFlag) {
            addRecord();
        } else if (etFlag) {
            Toast.makeText(requireContext(), "제목을 입력하세요!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "할 일을 입력하세요!", Toast.LENGTH_SHORT).show();
        }
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