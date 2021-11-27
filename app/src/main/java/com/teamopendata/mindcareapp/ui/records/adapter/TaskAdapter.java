package com.teamopendata.mindcareapp.ui.records.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.model.entity.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<Task> mItems;
    private Task cachedItem;
    private int cachedPosition = -1;

    public TaskAdapter(List<Task> item) {
        mItems = item;
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
        holder.bind(mItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addTask(Task task) {
        mItems.add(task);
    }

    public List<Task> getItem() {
        return mItems;
    }

    public boolean hasItem() {
        if (mItems.isEmpty()) return false;

        for (Task mItem : mItems) {
            if (!(mItem.getContents() == null))
                if (!mItem.getContents().equals("")) return true;
        }
        return false;
    }

    public List<Task> removeBlankItem() {
        List<Task> tasks = new ArrayList<>();
        for (Task task : mItems) {
            if (!(task.getContents() == null)) {
                if (!task.getContents().equals("")) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public void removeItem(int position) {
        cachedItem = mItems.get(position);
        cachedPosition = position;
        mItems.remove(position);
        //notifyItemRemoved(position);
       // notifyItemRangeChanged(position, mItems.size());
        // notifyItemRemoved()는 뭔가 잘안됨..
        notifyDataSetChanged();
    }

    public void revertItem() {
        mItems.add(cachedPosition, cachedItem);
        notifyItemInserted(cachedPosition);
    }

    public void swapItem(int fromPos, int toPos) {
        Collections.swap(mItems, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox cbCompletedTask;
        private final EditText etContentsTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCompletedTask = itemView.findViewById(R.id.cb_completed_task);
            etContentsTask = itemView.findViewById(R.id.et_contents_task);
        }

        public void bind(Task task, int position) {
            etContentsTask.setText(task.getContents());
            cbCompletedTask.setChecked(task.isCompleted());

            cbCompletedTask.setOnCheckedChangeListener((buttonView, isChecked) -> mItems.get(position).setCompleted(isChecked));
            etContentsTask.addTextChangedListener(new ContentsTextWatcher(position));
        }
    }

    class ContentsTextWatcher implements TextWatcher {
        int mPosition;

        public ContentsTextWatcher(int position) {
            mPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mItems.get(mPosition).setContents(s.toString());
        }
    }
}
