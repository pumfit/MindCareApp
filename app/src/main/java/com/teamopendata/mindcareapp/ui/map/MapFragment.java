package com.teamopendata.mindcareapp.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FrameLayout map_container;
    private MapListAdapter adapter;
    private ArrayList<String> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map_mian, container, false);
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

        Questionbtn.setOnClickListener(new View.OnClickListener() {//기관 설명 Dialog
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.callFunction();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {//구글맵 fragment를 불러오기 위함
        super.onViewCreated(view, savedInstanceState);

        map_container = (FrameLayout) view.findViewById(R.id.fl_map_container) ;

        GoogleMapFragment map = new GoogleMapFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_map_container, map).commit();
    }


}