package com.teamopendata.mindcareapp.ui.map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.teamopendata.mindcareapp.BtnPrefMgr;
import com.teamopendata.mindcareapp.MainActivity;
import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;
import java.util.List;



public class MapFragment extends Fragment implements GoogleMapFragment.CustomMapListener {

    public MapListAdapter adapter;

    public BottomSheetDialog bottomSheetDialog = null;
    private GoogleMapFragment googleMapFragment = null;
    private GpsTracker gpsTracker;
    public double latitude = 37.65373464975277d;
    public List<MedicalInstitution> list = null;
    public double longitude = 127.06081718059411d;

    public RecyclerView recyclerView = null;
    public ArrayList<String> userKeywordList = null;

    public final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            MapFragment.this.adapter = new MapListAdapter(MapFragment.this.list);
            MapFragment.this.recyclerView.setAdapter(MapFragment.this.adapter);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map_main, container, false);
       // View bottomSheetView = inflater.inflate(R.layout.layout_map_bottomsheet, null);


//        this.bottomSheetDialog.show();
        View view = ((MainActivity)getActivity()).bottomSheetParentLayout;
        view.setVisibility(View.VISIBLE);
//        view = inflater.inflate(R.layout.layout_map_bottomsheet, null);
//        bottomSheetDialog.setContentView(view);
 //       bottomSheetDialog = new BottomSheetDialog(container.getContext());

        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        this.userKeywordList = new ArrayList<>();
        this.userKeywordList = BtnPrefMgr.getStringArrayPref(container.getContext(), BtnPrefMgr.BTN_PREF_KEY);

        getCurrentAddress();

        new dataThread().start();

        ((ImageButton) root.findViewById(R.id.btn_map_question)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new CustomDialog(MapFragment.this.getActivity()).callFunction();
            }
        });
        ((ImageButton) root.findViewById(R.id.btn_map_bookmark)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new bookmarkThread().start();
                MapFragment.this.bottomSheetDialog.show();
            }
        });
        ((ImageButton) root.findViewById(R.id.btn_map_back)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.googleMapFragment = new GoogleMapFragment(this);
        getChildFragmentManager().beginTransaction().replace(R.id.fl_map_container, this.googleMapFragment).commit();
    }

    public void callBackMapReady() {
        this.googleMapFragment.addMediInfoMarker(this.list);
    }

    public void callBackClickMarker(LatLng position) {
        ArrayList<MedicalInstitution> m = new ArrayList<>();
        int i = 0;
        while (true) {
            if (i < this.list.size()) {
                if (this.list.get(i).latitude == position.latitude && this.list.get(i).longitude == position.longitude) {
                    m.add(this.list.get(i));
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        ((MainActivity)getActivity()).mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        this.recyclerView.setAdapter(new MapListAdapter(m));

    }

    @Override
    public void callBackClickRefresh(LatLng position) throws InterruptedException {
        longitude = position.longitude;
        latitude = position.latitude;
        Thread th = new dataThread();
        th.start();
        th.join();
        googleMapFragment.addMediInfoMarker(MapFragment.this.list);

        ((MainActivity)getActivity()).mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void getCurrentAddress() {
        GpsTracker gpsTracker2 = new GpsTracker(getContext());
        this.gpsTracker = gpsTracker2;
        this.latitude = gpsTracker2.getLatitude();
        this.longitude = this.gpsTracker.getLongitude();
    }

    /* renamed from: com.teamopendata.mindcareapp.ui.map.MapFragment$dataThread */
    class dataThread extends Thread {
        dataThread() {
        }

        public void run() {
            MindChargeDB db = MindChargeDB.getInstance(MapFragment.this.getContext());
            MapFragment.this.list = db.getMedicalInstitutionDao().getCurrentList(MapFragment.this.latitude, MapFragment.this.longitude);
            ArrayList<MedicalInstitution> recommedList = new ArrayList<>();
            for (int idx = 0; idx < list.size(); idx++)
            {
                for(int i=0;i<userKeywordList.size();i++)
                {
                    if (MapFragment.this.list.get(idx).keyword1.equals(MapFragment.this.userKeywordList.get(i)) || MapFragment.this.list.get(idx).keyword2.equals(MapFragment.this.userKeywordList.get(i)) || MapFragment.this.list.get(idx).keyword3.equals(MapFragment.this.userKeywordList.get(i)) || MapFragment.this.list.get(idx).keyword4.equals(MapFragment.this.userKeywordList.get(i)) || MapFragment.this.list.get(idx).keyword5.equals(MapFragment.this.userKeywordList.get(i)) || MapFragment.this.list.get(idx).keyword6.equals(MapFragment.this.userKeywordList.get(i)))
                    {
                        recommedList.add(MapFragment.this.list.get(idx));
                        break;
                    }
                }

            }
            MapFragment.this.list = recommedList;
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    }



    /* renamed from: com.teamopendata.mindcareapp.ui.map.MapFragment$bookmarkThread */
    class bookmarkThread extends Thread {
        bookmarkThread() {
        }

        public void run() {
            MapFragment.this.adapter = new MapListAdapter(MindChargeDB.getInstance(MapFragment.this.getContext()).getBookMarkDao().getBookmarkList());
            MapFragment.this.recyclerView.setAdapter(MapFragment.this.adapter);
        }
    }
}
