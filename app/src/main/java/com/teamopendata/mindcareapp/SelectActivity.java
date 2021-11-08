package com.teamopendata.mindcareapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SelectActivity extends AppCompatActivity {
    private final String TAG = SelectActivity.class.getSimpleName();

    android.widget.Button stressbutton,fearbutton,insomniabutton,completebutton;
    TextView picknum;
    int  stressbutton_status,fearbutton_status,insomniabutton_status ,sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);

        stressbutton = findViewById(R.id.stressbutton);
        fearbutton = findViewById(R.id.fearbutton);
        insomniabutton = findViewById(R.id.insomniabutton);
        completebutton = findViewById(R.id.completebutton);

        picknum = findViewById(R.id.picknum);

        stressbutton_status = 0;
        fearbutton_status = 0;
        insomniabutton_status = 0;
        sum = 0;

        stressbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(stressbutton_status == 0){
                    buttonClicked(stressbutton);
                    stressbutton_status = 1;
                    Log.d(TAG,"클릭시 sum: "+sum);
                }
                else if(stressbutton_status == 1){
                    buttonDefault(stressbutton);
                    stressbutton_status = 0;
                    Log.d(TAG,"클릭 다시 sum: "+sum);
                }

            }
        });

        fearbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(fearbutton_status == 0){
                    buttonClicked(fearbutton);
                    fearbutton_status = 1;

                }
                else if(fearbutton_status == 1){
                    buttonDefault(fearbutton);
                    fearbutton_status = 0;

                }

            }
        });

        insomniabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(insomniabutton_status == 0){
                    buttonClicked(insomniabutton);
                    insomniabutton_status = 1;
                }
                else if(insomniabutton_status == 1){
                    buttonDefault(insomniabutton);
                    insomniabutton_status = 0;
                }

            }
        });

        completebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void buttonClicked(android.widget.Button button ) {

        button.setBackgroundResource(R.drawable.buttonselected);
        button.setTextColor(getResources().getColor(R.color.white, getTheme()));
        sum++;
        picknum.setText(String.valueOf(sum));
    }
    private  void buttonDefault(android.widget.Button button){

        button.setBackgroundResource(R.drawable.button);
        button.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));
        sum--;
        picknum.setText(String.valueOf(sum));
    }


}
