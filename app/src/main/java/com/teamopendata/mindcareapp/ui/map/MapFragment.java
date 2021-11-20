package com.teamopendata.mindcareapp.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private MapListAdapter adapter;
    private ArrayList<String> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final Button Questionbtn = root.findViewById(R.id.questionButton);

        arrayList = new ArrayList<String>();

        //defalt 기관 정보 이후 DAO 생성하고 변경
        arrayList.add("노원구 정신건강복지센터");
        arrayList.add("노원구 중독관리통합지원센터");
        arrayList.add("노원 아이존");
        arrayList.add("노원 아이 정신과 의원");
        adapter = new MapListAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        Questionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.callFunction();
            }
        });

        return root;
    }
}
