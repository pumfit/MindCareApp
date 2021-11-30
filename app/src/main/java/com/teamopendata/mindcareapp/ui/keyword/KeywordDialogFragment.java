package com.teamopendata.mindcareapp.ui.keyword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentDailogKeywordBinding;

public class KeywordDialogFragment extends DialogFragment {
    private FragmentDailogKeywordBinding binding;
    private KeywordContainerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDailogKeywordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new KeywordContainerAdapter(requireContext());
        binding.rvKeywords.setAdapter(adapter);
    }

    @Override
    public int getTheme() {
        return R.style.KeywordDialogFragmentTheme;
    }

}
