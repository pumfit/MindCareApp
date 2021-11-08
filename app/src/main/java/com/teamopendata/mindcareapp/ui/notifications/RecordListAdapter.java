package com.teamopendata.mindcareapp.ui.notifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {

    private ArrayList<String> arrayList;

    public RecordListAdapter(ArrayList<String> list) { arrayList = list; }

    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.record_yourlist_item,parent,false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(view);

        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        String text = arrayList.get(position);
        Log.d("list",arrayList.get(position));
        holder.textView1.setText(text);
        holder.textView2.setText("처 방");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public TextView textView2;

        public RecordViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textDate);
            textView2 = itemView.findViewById(R.id.textRecordType);
        }

    }
}
