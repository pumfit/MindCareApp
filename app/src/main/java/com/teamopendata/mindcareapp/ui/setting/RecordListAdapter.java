package com.teamopendata.mindcareapp.ui.setting;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {

    private ArrayList<String> arrayList;

    public RecordListAdapter(ArrayList<String> list) { arrayList = list; }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textTitle);
            textView2 = itemView.findViewById(R.id.textType);
        }

    }
}
