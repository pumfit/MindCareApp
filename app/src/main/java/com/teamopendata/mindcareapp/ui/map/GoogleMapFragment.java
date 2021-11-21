package com.teamopendata.mindcareapp.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.teamopendata.mindcareapp.R;

public class GoogleMapFragment extends Fragment
{
    MapView mMap;
    //https://milkissboy.tistory.com/11?category=733108
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mMap = (MapView) v.findViewById(R.id.mf_google_map);
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    boolean permission = hasAllPermissionsGranted();
                    if (permission) {
                        Log.e("test","permission");
                    }
                    return;
                }
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMyLocationEnabled(true);

                try {
                    MapsInitializer.initialize(getActivity());
                } catch (Exception e) {

                }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.488, 126.891), 15));
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        mMap.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }

    //권한 추가
    private static final int REQUEST_PERMISSIONS = 1;
    private static final String[] MY_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private boolean hasAllPermissionsGranted() {
        for (String permission : MY_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), MY_PERMISSIONS, REQUEST_PERMISSIONS);
                return false;
            }
        }
        return true;
    }
}
