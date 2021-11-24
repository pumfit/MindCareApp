package com.teamopendata.mindcareapp.ui.records.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentRecordsHomeBinding;
import com.teamopendata.mindcareapp.ui.records.adapter.RecordsAdapter;
import com.teamopendata.mindcareapp.ui.records.StickyHeaderItemDecoration;
import com.teamopendata.mindcareapp.ui.records.item.RecordItem;
import com.teamopendata.mindcareapp.ui.records.listener.OnAddEditRecordListener;

import java.util.ArrayList;

public class HomeRecordsFragment extends Fragment {
    private static final String TAG = "HomeRecordsFragment";
    private FragmentRecordsHomeBinding binding;

    private Button btnRecordAdd, btnRecordEdit;

    private RecordsAdapter mRecordsAdapter;

    private OnAddEditRecordListener mListener = null;

    public void setOnAddRecordListener(OnAddEditRecordListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records_home, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getAll -> add recordItem
        ArrayList<RecordItem> items = new ArrayList<>();

        new Thread(() -> {
            MindChargeDB.getInstance(requireContext()).getRecordDao().getAll().forEach(record -> items.add(new RecordItem(record)));
            new Handler(Looper.getMainLooper()).post(() -> mRecordsAdapter.initItems(items));
        }).start();

        mRecordsAdapter = new RecordsAdapter(items, mListener);
        binding.rvRecordsList.setAdapter(mRecordsAdapter);
        binding.rvRecordsList.addItemDecoration(new StickyHeaderItemDecoration(mRecordsAdapter));
    }
}