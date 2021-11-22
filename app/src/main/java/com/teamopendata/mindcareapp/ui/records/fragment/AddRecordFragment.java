package com.teamopendata.mindcareapp.ui.records.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentAddRecordBinding;
import com.teamopendata.mindcareapp.ui.records.adapter.TaskAdapter;
import com.teamopendata.mindcareapp.ui.records.model.task.Task;

import java.util.ArrayList;

public class AddRecordFragment extends Fragment {
    private FragmentAddRecordBinding binding;
    private TaskAdapter taskAdapter;

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
        item.add(new Task("1일 1회 명상 & 심호흡 하기"));
        item.add(new Task("처방약 복용 잘하기(아침/저녁, 1일 2회, 7일분"));
        item.add(new Task("하루의 감정상태 기록하기"));
        item.add(new Task("잠자기 2시간 전에 샤워 마치기"));
        item.add(new Task("스마트폰 하루 사용시간 지키기(최대 4시간)"));
        taskAdapter = new TaskAdapter(item);
        binding.includeRv.rvRecordTask.setAdapter(taskAdapter);

        binding.includeRv.btnTaskAdd.setOnClickListener(v -> {
            taskAdapter.addTask(new Task());
            taskAdapter.notifyItemChanged(taskAdapter.getItemCount());
        });
    }
}