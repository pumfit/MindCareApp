package com.teamopendata.mindcareapp.ui.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    FrameLayout map_container;
    private MapListAdapter adapter;
    private ArrayList<String> arrayList;
    public RecyclerView recyclerView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map_mian, container, false);

        View bottomSheetView = inflater.inflate(R.layout.layout_map_bottomsheet, null);//기관리스트 BottomSheet 생성
        recyclerView = bottomSheetView.findViewById(R.id.recyclerview);

        bottomSheetView.canScrollVertically(200);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(container.getContext());//context
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        Thread th = new dataThread();
        th.start();

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
            MindChargeDB db = MindChargeDB.getInstance(getContext());
           // db.getMedicalInstitutionDao().insertAll(new MedicalInstitution("노원구정신건강복지센터","기초정신건강증진센터",3,"서울특별시 노원구 노해로 437","02-2116-4591","www.nowonmind.or.kr",127.0565,37.65427));
            List<MedicalInstitution> list = db.getMedicalInstitutionDao().getAll();
            adapter = new MapListAdapter(list);
            recyclerView.setAdapter(adapter);
            //GoogleMapFragment.addMarker(list);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
