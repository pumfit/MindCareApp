package com.teamopendata.mindcareapp.ui.records.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.ui.records.StickyHeaderItemDecoration;
import com.teamopendata.mindcareapp.ui.records.listener.OnAddEditRecordClickListener;

import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.ui.records.item.RecordItem;
import com.teamopendata.mindcareapp.common.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderItemDecoration.StickyHeaderInterface {

    public enum Type {
        TYPE_TOP_HEADER,
        TYPE_HEADER,
        TYPE_ITEM
    }

    public static class Header {
        private final LocalDate localDate;
        private int position;

        public Header(LocalDate localDate, int position) {
            this.localDate = localDate;
            this.position = position;
        }

        @NonNull
        @Override
        public String toString() {
            return "Header{" +
                    "localDate=" + localDate +
                    ", position=" + position +
                    '}';
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (getClass() != (obj != null ? obj.getClass() : null))
                return false;
            return this.localDate.equals(((Header) obj).localDate);
        }
    }


    private static final String TAG = "RecordsAdapter";
    private final List<RecordItem> mItems;

    /**
     * 새로 추가되는 아이템의 위치를 header들의 날짜와 비교해서 알맞은 위치에 추가한다.
     */
    private final List<Header> headers;

    private final OnAddEditRecordClickListener mAddEditRecordListener;

    public RecordsAdapter(List<RecordItem> data, OnAddEditRecordClickListener addEditRecordListener) {
        headers = new ArrayList<>();
        mItems = new ArrayList<>();
        mAddEditRecordListener = addEditRecordListener;
        initHeaderAndItem(data);
    }

    private void initHeaderAndItem(List<RecordItem> data) {
        if (mItems.isEmpty())
            mItems.add(new RecordItem(Type.TYPE_TOP_HEADER));

        data.forEach(recordItem -> addHeaderAndItem((Record) recordItem.getItem()));
        Log.d(TAG, "addHeaderAndItem: headers(index) -> " + headers.toString());
    }

    private void addHeaderAndItem(Record newRecord) {
        if (headers.isEmpty()) {
            insertHeaderAndItem(mItems.size(), 0, newRecord);
            return;
        }

        // item insert
        for (Header header : headers) {
            LocalDate hDate = header.localDate;
            LocalDate rDate = newRecord.getDate();

            if (hDate.getYear() > rDate.getYear())
                continue;

            // 1. hYear == rYear
            if (hDate.getYear() == rDate.getYear()) {
                // 1.1 hMonth == rMonth -> header 밑으로 아이템 비교 후 삽입
                if (hDate.getMonthValue() == rDate.getMonthValue()) {
                    int target = headers.get(headers.indexOf(header) + 1) != null ?
                            headers.get(headers.indexOf(header) + 1).position : mItems.size();
                    for (int i = header.position + 1; i < target; i++) {
                        LocalDate recordDate = ((Record) mItems.get(i).getItem()).getDate();

                        if (recordDate.compareTo(newRecord.getDate()) <= 0) {
                            mItems.add(i, new RecordItem(newRecord));
                            headerIncrementPos(header.position, 1);
                            return;
                        }
                    }
                    mItems.add(target, new RecordItem(newRecord));
                    headerIncrementPos(headers.indexOf(header), 1);
                    return;
                }
                // 1.2 hMonth > rMonth -> header 자리에 새로운 header 추가 후 아이템 추가
                else if (hDate.getMonthValue() < rDate.getMonthValue()) {
                    insertHeaderAndItem(header.position, headers.indexOf(header), newRecord);
                    return;
                }
                // 2. hYear > rYear -> header 자리에 새로운 header 추가 후 아이템 추가
            } else if (hDate.getYear() < rDate.getYear() || hDate.getMonthValue() < rDate.getMonthValue()) {
                insertHeaderAndItem(header.position, headers.indexOf(header), newRecord);
                return;
            }
        }
        // 3. 일치하는 header 없음 -> 맨 마지막에 추가
        insertHeaderAndItem(mItems.size() - 1, headers.size(), newRecord);
    }

    private void insertHeaderAndItem(int position, int listPosition, Record newRecord) {
        if (!headers.isEmpty()) headerIncrementPos(listPosition, 2);
        int itemPos = insertHeader(position, newRecord);
        mItems.add(itemPos, new RecordItem(newRecord));
    }

    private void headerIncrementPos(int pos, int increment) {
        for (int i = pos; i < headers.size(); i++) headers.get(i).position += increment;
    }

    private int insertHeader(int position, Record record) {
        Header header =
                new Header(LocalDate.of(record.getDate().getYear(), record.getDate().getMonthValue(), 1), position);
        mItems.add(position, new RecordItem(header));
        headers.add(header);
        headers.sort((o1, o2) -> o2.localDate.compareTo(o1.localDate));
        return header.position + 1;
    }

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
            ((HeaderViewHolder) holder).bind((Header) mItems.get(position).getItem());
        } else {
            ((RecordViewHolder) holder).bind((Record) mItems.get(position).getItem(), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType().ordinal();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initItems(ArrayList<RecordItem> items) {
        initHeaderAndItem(items);
        this.notifyDataSetChanged();
    }

    public void addItem(Record newRecord) {
        addHeaderAndItem(newRecord);
    }

    @Override
    public boolean isHeader(int position) {
        return mItems.get(position).getType() == Type.TYPE_HEADER;
    }

    @Override
    public View getHeaderLayoutView(RecyclerView parent, int position) {
        View header = LayoutInflater.from(parent.getContext()).inflate(getHeaderLayout(position), parent, false);
        if (mItems.get(position).getType() != Type.TYPE_TOP_HEADER)
            bindHeaderData(header, position);
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
    public void bindHeaderData(View view, int headerPosition) {
        String year, month;

        if (mItems.get(headerPosition).getType() == Type.TYPE_HEADER) {
            Header header = ((Header) mItems.get(headerPosition).getItem());
            year = header.localDate.getYear() + "년";
            month = header.localDate.getMonthValue() + "월";
        } else {
            Record record = ((Record) mItems.get(headerPosition).getItem());
            year = record.getDate().getYear() + "년";
            month = record.getDate().getMonthValue() + "월";
        }

        ((TextView) view.findViewById(R.id.tv_record_item_year)).setText(year);
        ((TextView) view.findViewById(R.id.tv_record_item_month)).setText(month);
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        if (mItems.get(headerPosition).getType() == Type.TYPE_TOP_HEADER)
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
        }

        public void bind(Record record, int position) {
            Log.d(TAG, "bind: " + record.toString());
            tvDayNumber.setText(String.valueOf(record.getDate().getDayOfMonth()));
            tvDayText.setText(Utils.getDayString(record.getDate().getDayOfWeek()));
            tvRecordTitle.setText(record.getTitle());

            btnRecordEdit.setOnClickListener(v -> mAddEditRecordListener.onAddEditRecordClick((Record) mItems.get(position).getItem()));
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvYear, tvMonth;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvYear = itemView.findViewById(R.id.tv_record_item_year);
            tvMonth = itemView.findViewById(R.id.tv_record_item_month);
        }

        public void bind(Header header) {
            String year = header.localDate.getYear() + "년";
            String month = header.localDate.getMonthValue() + "월";

            tvYear.setText(year);
            tvMonth.setText(month);
        }
    }

    private class TopHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnRecordAdd;

        public TopHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRecordAdd = itemView.findViewById(R.id.btn_record_add);
            btnRecordAdd.setOnClickListener(v -> mAddEditRecordListener.onAddEditRecordClick(null));
        }

        public void bind() {
        }
    }
}

