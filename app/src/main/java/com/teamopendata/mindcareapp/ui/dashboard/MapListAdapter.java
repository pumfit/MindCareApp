package com.teamopendata.mindcareapp.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.ViewHolder> {

    private ArrayList<String> arrayList;

    public MapListAdapter(ArrayList<String> list) { arrayList = list; }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.maplist_item,parent,false);
        MapListAdapter.ViewHolder viewHolder = new MapListAdapter.ViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = arrayList.get(position);
        holder.textView1.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayData(String strData) { arrayList.add(strData); }

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
