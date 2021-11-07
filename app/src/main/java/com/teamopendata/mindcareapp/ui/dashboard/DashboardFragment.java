package com.teamopendata.mindcareapp.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private MapListAdapter adapter;
    private ArrayList<String> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview);

        arrayList = new ArrayList<String>();
        for(int i=1;i<11;i++)
        {
            String str = i+"번째 : 내용";
            arrayList.add(str);


        }
        for (int i = 0;i<10;i++){
            Log.d("SENTI","array"+arrayList.get(i));
        }
        adapter = new MapListAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
