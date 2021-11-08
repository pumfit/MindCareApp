package com.teamopendata.mindcareapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

//        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
//        boolean checkFisrt = pref.getBoolean("checkFirst",false);
//        if(checkFisrt==false)//첫 시작시 튜토리얼 액티비티 실행
//        {
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putBoolean("checkFirst",true);
//            editor.commit();
//
//            Intent intent  = new Intent(MainActivity.this, SelectActivity.class);
//            startActivity(intent);
//
//            finish();
//        }else
//        {
//
//        }
        //!-- fragment 네이게이션 만드는 코드
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_stting, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
