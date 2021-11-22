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
import com.teamopendata.mindcareapp.ui.records.listener.OnAddRecordListener;


public class RecordsFragment extends Fragment implements OnAddRecordListener {
    public static final String TAG = "RecordsFragment";
    private FragmentRecordsBinding binding;

    HomeRecordsFragment homeRecordsFragment;
    AddRecordFragment addRecordFragment;

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

    @Override
    public void onAddRecordButtonClick() {
        addRecordFragment = new AddRecordFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_records_container, addRecordFragment).addToBackStack(AddRecordFragment.class.getName()).commit();
    }
}
