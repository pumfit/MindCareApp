package com.teamopendata.mindcareapp.ui.records;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.ui.records.model.Record;
import com.teamopendata.mindcareapp.ui.records.model.RecordItem;
import com.teamopendata.mindcareapp.util.Utils;

import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public enum Type {
        TYPE_HEADER,
        TYPE_ITEM
    }

    private static final String TAG = "RecordsAdapter";
    private List<RecordItem> mData;

    public RecordsAdapter(List<RecordItem> data) {
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type.TYPE_HEADER.ordinal()) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records_header, parent, false));
        } else {
            return new RecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind((int[]) mData.get(position).getItem());
        } else {
            ((RecordViewHolder) holder).bind((Record) mData.get(position).getItem());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType().ordinal();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private boolean isHeader(int position) {
        return mData.get(position).getType() == Type.TYPE_HEADER;
    }

    private static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayNumber, tvDayText, tvRecordTitle;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayNumber = itemView.findViewById(R.id.tv_record_day_number);
            tvDayText = itemView.findViewById(R.id.tv_record_day_text);
            tvRecordTitle = itemView.findViewById(R.id.tv_record_title);
        }

        public void bind(Record record) {
            Log.d(TAG, "bind: " + record.toString());
            tvDayNumber.setText(String.valueOf(record.getDate().getDayOfMonth()));
            tvDayText.setText(Utils.getDayString(record.getDate().getDayOfWeek()));
            tvRecordTitle.setText(record.getTitle());
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvYear, tvMonth;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvYear = itemView.findViewById(R.id.tv_record_item_year);
            tvMonth = itemView.findViewById(R.id.tv_record_item_month);
        }

        public void bind(int[] date) {
            String year = date[0] + "년";
            String month = date[1] + "월";
            tvYear.setText(year);
            tvMonth.setText(month);
        }
    }
}
