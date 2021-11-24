package com.teamopendata.mindcareapp.ui.records.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentAddRecordBinding;
import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.ui.records.adapter.TaskAdapter;
import com.teamopendata.mindcareapp.common.model.entity.Task;
import com.teamopendata.mindcareapp.common.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddRecordFragment extends Fragment {
    private static final String TAG = "AddRecordFragment";
    private FragmentAddRecordBinding binding;
    private TaskAdapter taskAdapter;

    Calendar cal = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_record, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Task> item = new ArrayList<>();
        taskAdapter = new TaskAdapter(item);
        binding.includeRv.rvRecordTask.setAdapter(taskAdapter);

        binding.includeRv.btnTaskAdd.setOnClickListener(v -> {
            taskAdapter.addTask(new Task());
            taskAdapter.notifyItemChanged(taskAdapter.getItemCount());
        });

        binding.btnRecordSave.setOnClickListener(v -> {
            new Thread(() -> MindChargeDB.getInstance(requireContext()).getRecordDao().insert(newRecord())).start();
            Utils.buildSaveToast(view, requireContext(), getLayoutInflater()).show();
        });

        String date = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE);
        binding.tvRecordPickDate.setText(date);

        binding.tvRecordPickDate.setOnClickListener(v -> showDatePickerDialog());

    }

    /**
     * DatePickerListener
     */
    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            binding.tvRecordPickDate.setText(String.format(Locale.KOREAN, "%d-%d-%d", year, month, dayOfMonth));
        }
    };

    private void showDatePickerDialog() {
        new DatePickerDialog(requireContext(), mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
    }

    private Record newRecord() {
        Record record = new Record(binding.etRecordTitle.getText().toString(), LocalDate.now());
        Log.d(TAG, "newTask:" + taskAdapter.getItem());
        record.setTasks(taskAdapter.getItem());
        Log.d(TAG, "newRecord: " + record.toString());
        return record;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }
}