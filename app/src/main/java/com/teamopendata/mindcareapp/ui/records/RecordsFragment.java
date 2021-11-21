package com.teamopendata.mindcareapp.ui.records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentRecordsBinding;
import com.teamopendata.mindcareapp.ui.records.model.Record;
import com.teamopendata.mindcareapp.ui.records.model.RecordItem;

import java.time.LocalDate;
import java.util.ArrayList;


public class RecordsFragment extends Fragment {
    private FragmentRecordsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<RecordItem> list = new ArrayList<>();
        list.add(new RecordItem(new int[]{2021, 5}, RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("서울병원 처방", LocalDate.of(2021, 5, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("대구병원 처방", LocalDate.of(2021, 5, 15)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new int[]{2021, 6}, RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("부산병원 처방", LocalDate.of(2021, 6, 3)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new int[]{2021, 7}, RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("제주도병원 처방", LocalDate.of(2021, 7, 5)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new int[]{2021, 8}, RecordsAdapter.Type.TYPE_HEADER));
        list.add(new RecordItem(new Record("경북병원 처방", LocalDate.of(2021, 8, 13)), RecordsAdapter.Type.TYPE_ITEM));
        list.add(new RecordItem(new Record("마산병원 처방", LocalDate.of(2021, 8, 3)), RecordsAdapter.Type.TYPE_ITEM));

        binding.rvRecordsList.setAdapter(new RecordsAdapter(list));
    }
}
