package com.teamopendata.mindcareapp.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class RecordAddListAdapter extends RecyclerView.Adapter<RecordAddListAdapter.RecordViewHolder>{


    private ArrayList<String> arrayList;

    public RecordAddListAdapter(ArrayList<String> list) { arrayList = list; }

    @Override
    public RecordAddListAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        RecordAddListAdapter.RecordViewHolder recordViewHolder;
        switch (viewType)
        {
            case 0 :
                view  = inflater.inflate(R.layout.record_yourlist_add_item,parent,false);
                recordViewHolder = new RecordAddListAdapter.RecordViewHolder(view);
                return recordViewHolder;
            case 1:
                view  = inflater.inflate(R.layout.add_record_item,parent,false);
                recordViewHolder = new RecordAddListAdapter.RecordViewHolder(view);
                return recordViewHolder;
        }
        view  = inflater.inflate(R.layout.add_record_item,parent,false);
        recordViewHolder = new RecordAddListAdapter.RecordViewHolder(view);
        return recordViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==arrayList.size()-1) {
            arrayList.add("add");
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {

        public RecordViewHolder(View itemView) {
            super(itemView);
        }

    }
}
