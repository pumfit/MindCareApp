package com.teamopendata.mindcareapp.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamopendata.mindcareapp.R;

import java.util.List;

public class GoogleMapFragment extends Fragment
{
    private static List<MedicalInstitution> list;
    private GpsTracker gpsTracker;
    private static GoogleMap gMap;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;//for GPS Tracking

    private MapView mMap;
    public double latitude = 37.65373464975277;
    public double longitude= 127.06081718059411;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_googlemap, container, false);
        mMap = (MapView) v.findViewById(R.id.mv_mapview);
        mMap.onCreate(savedInstanceState);

        if (!checkLocationServicesStatus()) {//GPS 기능 사용가능한지 확인
            Intent callGPSSettingIntent
                    = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);//아닌경우 Dialog 생성
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
        } else {
            checkRunTimePermission();
        }
        //getCurrentAddress();
        //Log.d("??",latitude+" "+longitude);//위경도를 애뮬레이터 상에서 불러오지못함 (구글 본사 or 0,0뜸,디바이스 빌드시엔 정상작동)
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                getCurrentAddress();
                LatLng currentPlace = new LatLng(latitude, longitude); //LatLng 위경도 좌표를 나타내는 클래스
                //Log.d("??",latitude+" "+longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(currentPlace);
                markerOptions.title("현위치");
                markerOptions.snippet("간단한 설명");
                googleMap.addMarker(markerOptions);//마커를 맵 객체에 추가함
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPlace));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            }
        });
        return v;
    }

    public static void addMarker(List<MedicalInstitution> mediList)
    {
        list = mediList;
        MarkerOptions markerOptions = new MarkerOptions();
        for(int i=0;i<list.size();i++)
        {
            markerOptions.position(new LatLng(list.get(i).latitude,list.get(i).longitude));
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