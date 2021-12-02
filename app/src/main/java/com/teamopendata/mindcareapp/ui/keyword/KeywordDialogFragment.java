package com.teamopendata.mindcareapp.ui.keyword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.SharedPreferencesManager;
import com.teamopendata.mindcareapp.databinding.FragmentDailogKeywordBinding;

import java.util.List;

public class KeywordDialogFragment extends DialogFragment {
    private FragmentDailogKeywordBinding binding;

    private KeywordContainerAdapter adapter;
    private KeywordContainerAdapter.OnSaveFinishedListener parentListener;

    private final KeywordContainerAdapter.OnSaveFinishedListener listener = keywords -> {
        parentListener.onFinished(keywords);
        dismiss();
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> keywords = SharedPreferencesManager.getUserKeywords(requireContext());
        if (keywords == null)
            adapter = new KeywordContainerAdapter(requireContext(), listener);
        else
            adapter = new KeywordContainerAdapter(requireContext(), keywords, listener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDailogKeywordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvKeywords.setAdapter(adapter);
        binding.btnKeywordDescription.setOnClickListener(v -> {
            new CenterDescriptionDialogFragment().show(getChildFragmentManager(),"CenterDescription");
        });
    }

    @Override
    public int getTheme() {
        return R.style.KeywordDialogFragmentTheme;
    }

    public void setOnFinishedListener(KeywordContainerAdapter.OnSaveFinishedListener listener) {
        parentListener = listener;
    }

}
