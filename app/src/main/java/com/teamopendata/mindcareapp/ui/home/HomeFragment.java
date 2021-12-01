package com.teamopendata.mindcareapp.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.teamopendata.mindcareapp.common.MindChargeDB;
import com.teamopendata.mindcareapp.common.SharedPreferencesManager;
import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.common.model.entity.Task;
import com.teamopendata.mindcareapp.databinding.FragmentHomeBinding;
import com.teamopendata.mindcareapp.ui.home.adapter.KeywordAdapter;
import com.teamopendata.mindcareapp.ui.keyword.KeywordContainerAdapter;
import com.teamopendata.mindcareapp.ui.keyword.KeywordDialogFragment;
import com.teamopendata.mindcareapp.ui.records.adapter.TaskAdapter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private KeywordDialogFragment keywordDialogFragment;

    private KeywordAdapter keywordAdapter;
    private TaskAdapter taskAdapter;

    private final String TAG = "HomeFragment";

    private int mindChargeFlag = -1;

    private Record cachedRecord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> keywords = SharedPreferencesManager.getUserKeywords(requireContext());
        keywordAdapter = new KeywordAdapter();

        if (keywords == null) showKeywordDialog();
        else keywordAdapter.updateItem(keywords);

        getTodayTasks();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.appbarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            //Log.d(TAG, "verticalOffset: " + verticalOffset);
            updateViews(Math.abs(((float) verticalOffset) / ((float) appBarLayout.getTotalScrollRange())));
        });

        binding.rvHomeKeyword.setEmptyView(binding.tvKeywordEmptyView);
        binding.rvHomeKeyword.setAdapter(keywordAdapter);

        binding.rvHomeTasks.setEmptyView(binding.tvTaskEmptyView);
        binding.rvHomeTasks.setAdapter(taskAdapter);
        binding.rvHomeTasks.addItemDecoration(new DividerItemDecoration(requireContext(), 1));

        binding.btnHomeKeywordSetting.setOnClickListener(v -> showKeywordDialog());
    }

    private void showKeywordDialog() {
        keywordDialogFragment = new KeywordDialogFragment();
        keywordDialogFragment.setOnFinishedListener((keywords) -> keywordAdapter.updateItem(keywords));
        keywordDialogFragment.show(getParentFragmentManager(), "KeywordDialog");
    }

    private void getTodayTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        new Thread(() -> {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = LocalDate.now().minus(Period.ofDays(1));

            // Log.d(TAG, "getTodayTasks: " + today.toString() + "," + yesterday.toString());
            cachedRecord = MindChargeDB.getInstance(getContext()).getRecordDao().getTasks(today, yesterday);

            if (cachedRecord != null) {
                Log.d(TAG, "getTodayTasks: " + cachedRecord.toString());
                tasks.addAll(cachedRecord.getTasks());

                new Handler(Looper.getMainLooper()).post(() -> taskAdapter.initItems(tasks));
            }
        }).start();

        taskAdapter = new TaskAdapter(this::updateMindChargeView);
        taskAdapter.setEditable(false);
    }

    private void updateViews(float offset) {
        if (mindChargeFlag != taskAdapter.getMindCharge()) {
            int mindCharge = taskAdapter.getMindCharge();
            mindChargeFlag = mindCharge;
            updateMindChargeView(mindCharge);
        }

        LinearLayout llHomeContainerCollapsed = binding.llHomeContainerCollapsed;
        if (offset > 0.25f) {
            llHomeContainerCollapsed.animate().alpha(1.0f);
            llHomeContainerCollapsed.setVisibility(View.VISIBLE);
        } else {
            llHomeContainerCollapsed.animate().alpha(0.0f);
            llHomeContainerCollapsed.setVisibility(View.GONE);
        }
    }

    public void updateMindChargeView(int progress) {

        int collapseBitmapId = getResources().getIdentifier("icon_mind_charge_collapsed_" + progress, "drawable", requireContext().getPackageName());
        int expandedBitmapId = getResources().getIdentifier("icon_mind_charge_expanded_" + progress, "drawable", requireContext().getPackageName());

        binding.ivHomeMindChargeExpanded.setImageDrawable(AppCompatResources.getDrawable(requireContext(), expandedBitmapId));
        binding.ivHomeMindChargeCollapsed.setImageDrawable(AppCompatResources.getDrawable(requireContext(), collapseBitmapId));

        int collapseTextId = getResources().getIdentifier("mind_charge_home_collapsed_" + progress, "string", requireContext().getPackageName());
        int expandedTextId = getResources().getIdentifier("mind_charge_home_expanded_" + progress, "string", requireContext().getPackageName());

        binding.tvHomeMindChargeExpended.setText(getResources().getString(expandedTextId));
        binding.tvHomeMindChargeCollapsed.setText(getResources().getString(collapseTextId));
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        if (cachedRecord != null) {
            new Thread(() -> MindChargeDB.getInstance(requireContext()).getRecordDao().update(cachedRecord)).start();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}