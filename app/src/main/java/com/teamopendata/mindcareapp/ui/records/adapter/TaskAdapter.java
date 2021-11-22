package com.teamopendata.mindcareapp.ui.records.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.ui.records.model.task.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<Task> mData = null;

    public TaskAdapter(List<Task> item) {
        mData = item;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addTask(Task task) {
        mData.add(task);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_task);
        }

        public void bind(Task task) {
            checkBox.setText(task.getContents());
            checkBox.setChecked(task.isCompleted());
        }
    }
}
