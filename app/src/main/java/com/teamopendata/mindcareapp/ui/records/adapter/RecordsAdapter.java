package com.teamopendata.mindcareapp.ui.records.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.ui.records.StickyHeaderItemDecoration;
import com.teamopendata.mindcareapp.ui.records.listener.OnAddEditRecordListener;

import com.teamopendata.mindcareapp.ui.records.model.record.Record;
import com.teamopendata.mindcareapp.ui.records.model.record.RecordHeader;
import com.teamopendata.mindcareapp.ui.records.model.record.RecordItem;
import com.teamopendata.mindcareapp.util.Utils;

import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderItemDecoration.StickyHeaderInterface {

    public enum Type {
        TYPE_TOP_HEADER,
        TYPE_HEADER,
        TYPE_ITEM
    }

    private static final String TAG = "RecordsAdapter";
    private final List<RecordItem> mData;

    private final OnAddEditRecordListener mRecordEditListener;

    public RecordsAdapter(List<RecordItem> data, OnAddEditRecordListener recordEditListener) {
        mData = data;
        mRecordEditListener = recordEditListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: " + viewType);
        if (viewType == Type.TYPE_TOP_HEADER.ordinal()) {
            return new TopHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records_top_header, parent, false));
        } else if (viewType == Type.TYPE_HEADER.ordinal()) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records_header, parent, false));
        } else {
            return new RecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopHeaderViewHolder) {
            ((TopHeaderViewHolder) holder).bind();
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind((RecordHeader) mData.get(position).getItem());
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

    @Override
    public boolean isHeader(int position) {
        return mData.get(position).getType() == Type.TYPE_HEADER;
    }

    @Override
    public View getHeaderLayoutView(RecyclerView parent, int position) {
        View header = LayoutInflater.from(parent.getContext()).inflate(getHeaderLayout(position), parent, false);
        if (mData.get(position).getType() != Type.TYPE_TOP_HEADER) bindHeaderData(header, position);
        return header;
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        RecordHeader item = ((RecordHeader) mData.get(headerPosition).getItem());

        String year = item.getDate().getYear() + "년";
        String month = item.getDate().getMonthValue() + "월";

        ((TextView) header.findViewById(R.id.tv_record_item_year)).setText(year);
        ((TextView) header.findViewById(R.id.tv_record_item_month)).setText(month);
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        if (mData.get(headerPosition).getType() == Type.TYPE_TOP_HEADER)
            return R.layout.item_records_top_header;
        else {
            return R.layout.item_records_header;
        }
    }


    private class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayNumber, tvDayText, tvRecordTitle;
        ImageButton btnRecordEdit;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayNumber = itemView.findViewById(R.id.tv_record_day_number);
            tvDayText = itemView.findViewById(R.id.tv_record_day_text);
            tvRecordTitle = itemView.findViewById(R.id.tv_record_title);
            btnRecordEdit = itemView.findViewById(R.id.btn_record_edit);
            btnRecordEdit.setOnClickListener(v -> mRecordEditListener.onAddEditRecordButtonClick());

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

        public void bind(RecordHeader date) {
            String year = date.getDate().getYear() + "년";
            String month = date.getDate().getMonthValue() + "월";

            tvYear.setText(year);
            tvMonth.setText(month);
        }
    }

    private class TopHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnRecordAdd;

        public TopHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRecordAdd = itemView.findViewById(R.id.btn_record_add);
            btnRecordAdd.setOnClickListener(v -> mRecordEditListener.onAddEditRecordButtonClick());
        }

        public void bind() {
        }
    }
}
