package com.teamopendata.mindcareapp.ui.records.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentRecordsBinding;
import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.ui.records.listener.OnAddEditRecordClickListener;

public class RecordsFragment extends Fragment implements OnAddEditRecordClickListener {
    public static final String TAG = "RecordsFragment";
    private FragmentRecordsBinding binding;

    HomeRecordsFragment homeRecordsFragment;
    AddEditRecordFragment addEditRecordFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeRecordsFragment = new HomeRecordsFragment();
        homeRecordsFragment.setOnAddRecordListener(this);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fl_records_container, homeRecordsFragment).commit();
    }

    public void onAddEditRecordClick(Record record) {
        addEditRecordFragment = new AddEditRecordFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_records_container, addEditRecordFragment).addToBackStack(AddEditRecordFragment.class.getName()).commit();
    }

}
