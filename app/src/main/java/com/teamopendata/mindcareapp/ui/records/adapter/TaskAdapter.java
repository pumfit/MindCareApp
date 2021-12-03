package com.teamopendata.mindcareapp.ui.records.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.model.entity.Task;
import com.teamopendata.mindcareapp.ui.home.listener.OnChangedTaskCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO 기록 하루에 하나만 작성가능 validation
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final String TAG = "TaskAdapter";
    private Task cachedItem;

    private int cachedPosition;
    private boolean editFlag = true;

    private List<Task> mItems;

    private final OnChangedTaskCompleteListener mListener;
    private float mindCharge = 0;

    public int validationFocus = -1;

    public TaskAdapter(List<Task> item) {
        mItems = item;
        mListener = null;
    }

    public TaskAdapter(OnChangedTaskCompleteListener listener) {
        mItems = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(holder, mItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setEditable(boolean editable) {
        editFlag = editable;
    }

    public float getMindCharge() {
        return mindCharge * 100;
    }

    public void addTask(Task task) {
        mItems.add(task);
    }

    public List<Task> getItem() {
        return mItems;
    }

    public boolean hasItem() {
        if (mItems.isEmpty()) {
            return false;
        }
        for (Task mItem : mItems) {
            if (!(mItem.getContents() == null || mItem.getContents().equals(""))) {
                return true;
            }
        }
        return false;
    }

    public List<Task> removeBlankItem() {
        List<Task> tasks = new ArrayList<>();
        for (Task task : mItems) {
            if (task.getContents() != null && !task.getContents().equals("")) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int position) {
        cachedItem = mItems.get(position);
        cachedPosition = position;
        mItems.remove(position);
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

    @SuppressLint("NotifyDataSetChanged")
    public void initItems(List<Task> tasks) {
        mItems = tasks;
        if (!mItems.isEmpty()) {
            setMindCharge();
            notifyDataSetChanged();
        }
    }

    public void setMindCharge() {
        int completeTask = 0;
        for (Task task : mItems) {
            if (task.isCompleted()) {
                completeTask++;
            }
        }
        float charge = (float) completeTask / mItems.size();
        Log.d(TAG, "setMindCharge: " + charge);
        if (charge >= 0f && charge < 0.25f) {
            mindCharge = charge;
        } else if (charge >= 0.25f && charge < 0.5f) {
            mindCharge = charge;
        } else if (charge >= 0.5f && charge < 0.75f) {
            mindCharge = charge;
        } else if (charge >= 0.75f && charge < 1.0d) {
            mindCharge = charge;
        } else {
            mindCharge = charge;
        }
    }

    public boolean validationItem() {
        for (Task item : mItems) {
            if (item.getContents() == null || item.getContents().length() == 0) {
                // validationFocus = mItems.indexOf(item);
                return false;
            }
        }
        // validationFocus = -1;
        return true;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox cbCompletedTask;
        private final EditText etContentsTask;
        private final ImageView ivTaskDrag;

        public TaskViewHolder(View itemView) {
            super(itemView);
            cbCompletedTask = itemView.findViewById(R.id.cb_completed_task);
            etContentsTask = itemView.findViewById(R.id.et_contents_task);
            ivTaskDrag = itemView.findViewById(R.id.iv_task_item_drag);
        }

        public void bind(RecyclerView.ViewHolder holder, Task task, int position) {
            if (!editFlag) {
                etContentsTask.setFocusable(false);
                ivTaskDrag.setVisibility(View.GONE);
            }
            etContentsTask.setText(task.getContents());
            cbCompletedTask.setChecked(task.isCompleted());
            cbCompletedTask.setOnCheckedChangeListener((buttonView, isChecked) -> updateCharge(position, isChecked));
            etContentsTask.addTextChangedListener(new ContentsTextWatcher(position, holder));
        }

        public void updateCharge(int position, boolean isChecked) {
            mItems.get(position).setCompleted(isChecked);
            setMindCharge();
            if (mListener != null) mListener.onChanged(mindCharge);
        }
    }

    public class ContentsTextWatcher implements TextWatcher {
        RecyclerView.ViewHolder holder;
        int mPosition;

        public ContentsTextWatcher(int position, RecyclerView.ViewHolder vh) {
            mPosition = position;
            holder = vh;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mItems.get(holder.getAdapterPosition()).setContents(s.toString());
        }
    }
}