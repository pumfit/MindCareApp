package com.teamopendata.mindcareapp.ui.records.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentRecordsHomeBinding;
import com.teamopendata.mindcareapp.ui.records.adapter.RecordsAdapter;
import com.teamopendata.mindcareapp.ui.records.StickyHeaderItemDecoration;
import com.teamopendata.mindcareapp.ui.records.listener.OnAddRecordListener;
import com.teamopendata.mindcareapp.ui.records.model.record.Record;
import com.teamopendata.mindcareapp.ui.records.model.record.RecordHeader;
import com.teamopendata.mindcareapp.ui.records.model.record.RecordItem;

import java.time.LocalDate;
import java.util.ArrayList;

public class HomeRecordsFragment extends Fragment {
    private FragmentRecordsHomeBinding binding;

    private RecordsAdapter mRecordsAdapter;

    private OnAddRecordListener mListener = null;

    public void setOnAddRecordListener(OnAddRecordListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<RecordItem> list = new ArrayList<>();
        list.add(new RecordItem(new RecordHeader(LocalDate.of(2021, 5, 1)), RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("서울병원 처방", LocalDate.of(2021, 5, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("대구병원 처방", LocalDate.of(2021, 5, 15)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("대구병원 처방", LocalDate.of(2021, 5, 15)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("대구병원 처방", LocalDate.of(2021, 5, 15)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("대구병원 처방", LocalDate.of(2021, 5, 15)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new RecordHeader(LocalDate.of(2021, 6, 1)), RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("부산병원 처방", LocalDate.of(2021, 6, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new RecordHeader(LocalDate.of(2021, 7, 1)), RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("제주도병원 처방", LocalDate.of(2021, 7, 5)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new RecordHeader(LocalDate.of(2021, 8, 1)), RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("경북병원 처방", LocalDate.of(2021, 8, 13)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("마산병원 처방", LocalDate.of(2021, 8, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("마산병원 처방", LocalDate.of(2021, 8, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("마산병원 처방", LocalDate.of(2021, 8, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("마산병원 처방", LocalDate.of(2021, 8, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("마산병원 처방", LocalDate.of(2021, 8, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("마산병원 처방", LocalDate.of(2021, 8, 3)), RecordsAdapter.Type.TYPE_ITEM));

        mRecordsAdapter = new RecordsAdapter(list);
        binding.rvRecordsList.setAdapter(mRecordsAdapter);
        binding.rvRecordsList.addItemDecoration(new StickyHeaderItemDecoration(mRecordsAdapter));

        binding.btnRecordAdd.setOnClickListener(v -> {
            mListener.onAddRecordButtonClick();
        });
    }
}