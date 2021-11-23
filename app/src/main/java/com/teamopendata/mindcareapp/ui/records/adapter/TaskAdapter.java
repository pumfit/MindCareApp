package com.teamopendata.mindcareapp.ui.records.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.model.entity.Task;

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
        private final CheckBox cbCompletedTask;
        private final EditText etContentsTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCompletedTask = itemView.findViewById(R.id.cb_task);
            etContentsTask = itemView.findViewById(R.id.et_contents_task);
        }

        public void bind(Task task) {
            etContentsTask.setText(task.getContents());
            cbCompletedTask.setChecked(task.isCompleted());
        }
    }
}
