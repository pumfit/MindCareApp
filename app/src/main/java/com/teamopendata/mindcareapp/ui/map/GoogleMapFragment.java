package com.teamopendata.mindcareapp.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamopendata.mindcareapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleMapFragment extends Fragment
{
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;//for GPS Tracking

    private GpsTracker gpsTracker;
    private GoogleMap gMap;
    private MapView mMap;

    public double latitude = 37.65373464975277;
    public double longitude= 127.06081718059411;

    public static Map<String, Float> colormap = new HashMap() {
        {
            put("광역형정신건강증진센터", Float.valueOf(0.0f));
            put("기초정신건강증진센터", Float.valueOf(30.0f));
            put("자살예방센터", Float.valueOf(60.0f));
            put("중독관리통합지원센터", Float.valueOf(120.0f));
            put("사회복귀시설", Float.valueOf(240.0f));
            put("정신의료기관", Float.valueOf(255.0f));
            put("정신요양시설", Float.valueOf(270.0f));
        }
    };

    private CustomMapListener listener;

    GoogleMapFragment(CustomMapListener listener)
    {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_googlemap, container, false);
        mMap = (MapView) v.findViewById(R.id.mv_mapview);
        mMap.onCreate(savedInstanceState);

        getCurrentAddress();
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                LatLng currentPlace = new LatLng(latitude, longitude); //LatLng 위경도 좌표를 나타내는 클래스
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(currentPlace);
                markerOptions.title("현위치");
                markerOptions.snippet("간단한 설명");
                googleMap.addMarker(markerOptions);//마커를 맵 객체에 추가함
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPlace));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                listener.callBackMapReady();

                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                        listener.callBackClickMarker(marker.getPosition());//TODO: marker가 반환하는 정보는 위경도인데 해당되는 정보를 전달하면 어떻게 해야할까요?
                        return true;
                    }
                });
            }
        });
        return v;
    }

    public interface CustomMapListener{
        void callBackMapReady();
        void callBackClickMarker(LatLng position);
    }

    public void addMediInfoMarker(List<MedicalInstitution> list)
    {
        MarkerOptions markerOptions = new MarkerOptions();
        for(int i=0;i<list.size();i++)
        {
            markerOptions.position(new LatLng(list.get(i).latitude,list.get(i).longitude));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(colormap.get(list.get(i).type)));
            markerOptions.title("기관");
            markerOptions.snippet("간단한 설명");
            gMap.addMarker(markerOptions);
        }
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

    public void getUserInstitution()//TODO : 기관 추천 처리
    {

    }

    public void getCurrentAddress()//현 주소를 받아온다.
    {
        gpsTracker = new GpsTracker(getContext());//현위치 GPS로 받아오기
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
    }

    public boolean checkLocationServicesStatus() { //GPS기능 사용가능한지 판단
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission() {//RunTimePermission 처리

        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
        } else {

            if (shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(getContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                requestPermissions(REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);//위치 정보 퍼미션 재요청

            } else {
                requestPermissions(REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }
}