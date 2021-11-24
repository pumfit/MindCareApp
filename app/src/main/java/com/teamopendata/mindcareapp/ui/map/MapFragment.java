package com.teamopendata.mindcareapp.ui.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.mindChargeDB;

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
        //final Button Questionbtn = root.findViewById(R.id.questionButton);

        arrayList = new ArrayList<String>();

        //defalt 기관 정보 이후 DAO 생성하고 변경
        arrayList.add("노원구 정신건강복지센터");
        arrayList.add("노원구 중독관리통합지원센터");
        arrayList.add("노원 아이존");
        arrayList.add("노원 아이 정신과 의원");

        Thread th = new dataThread();
        th.start();
/*
        Questionbtn.setOnClickListener(new View.OnClickListener() {//기관 설명 Dialog
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.callFunction();
            }
        });
 */

        View bottomSheetView = inflater.inflate(R.layout.layout_map_bottomsheet, null);//기관리스트 BottomSheet 생성
        bottomSheetView.canScrollVertically(200);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(container.getContext());//context
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        final RecyclerView recyclerView = bottomSheetView.findViewById(R.id.recyclerview);
        adapter = new MapListAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        SharedPreferences pref =  this.getActivity().getSharedPreferences("isFirst", Context.MODE_PRIVATE);//화면 첫 입장이라면 설명 CustomDialog 생성
        boolean first = pref.getBoolean("isFirst", false);

        if(first==false){
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst",true);
            editor.commit();
            CustomDialog customDialog = new CustomDialog(getActivity());
            customDialog.callFunction();
        }

        return root;
    }

    class dataThread extends Thread{
        public void run(){
            mindChargeDB db = mindChargeDB.getInstance(getContext());
            if(db != null)
            {

                Log.d("db","ddddddddddddddddd");
            }

        }
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
