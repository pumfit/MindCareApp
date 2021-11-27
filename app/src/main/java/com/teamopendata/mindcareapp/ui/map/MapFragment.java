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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements GoogleMapFragment.CustomMapListener{

    FrameLayout map_container;

    public RecyclerView recyclerView = null;
    private GoogleMapFragment googleMapFragment = null;
    private BottomSheetDialog bottomSheetDialog = null;
    private MapListAdapter adapter;

    public List<MedicalInstitution> list = null;

    public double latitude = 37.65373464975277;
    public double longitude= 127.06081718059411;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map_mian, container, false);
        View bottomSheetView = inflater.inflate(R.layout.layout_map_bottomsheet, null);//기관리스트 BottomSheet 생성
        recyclerView = bottomSheetView.findViewById(R.id.recyclerview);

        //bottomSheetView.canScrollVertically(200);
        bottomSheetDialog = new BottomSheetDialog(container.getContext());//context
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        SharedPreferences pref =  this.getActivity().getSharedPreferences("isFirst", Context.MODE_PRIVATE);//화면 첫 입장이라면 설명 CustomDialog 생성
        boolean first = pref.getBoolean("isFirst", false);

        Thread th = new dataThread();
        th.start();

        if(first==false){
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst",true);
            editor.commit();
            CustomDialog customDialog = new CustomDialog(getActivity());
            customDialog.callFunction();
        }

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {//구글맵 fragment를 불러오기 위함
        super.onViewCreated(view, savedInstanceState);

        map_container = (FrameLayout) view.findViewById(R.id.fl_map_container) ;

        googleMapFragment = new GoogleMapFragment(this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_map_container, googleMapFragment).commit();
    }

    @Override
    public void callBackMapReady() {
        googleMapFragment.addMediInfoMarker(list);
    }

    @Override
    public void callBackClickMarker(LatLng position) {
        ArrayList<MedicalInstitution> m = new ArrayList<MedicalInstitution>();
        for (int i=0;i<list.size();i++)
        {
            if(list.get(i).latitude==position.latitude&&list.get(i).longitude== position.longitude)
            {
                m.add(list.get(i));
                break;
            }
        }

        MapListAdapter adapter = new MapListAdapter(m);
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.show();
    }

    class dataThread extends Thread{
        public void run(){
            MindChargeDB db = MindChargeDB.getInstance(getContext());
            list = db.getMedicalInstitutionDao().getCurrentList(latitude,longitude);
            adapter = new MapListAdapter(list);
            recyclerView.setAdapter(adapter);
        }
    }

}
