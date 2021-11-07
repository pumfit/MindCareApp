package com.teamopendata.mindcareapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SelectActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    android.widget.Button stressbutton,fearbutton,insomniabutton,completebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);

        stressbutton = findViewById(R.id.stressbutton);
        fearbutton = findViewById(R.id.fearbutton);
        insomniabutton = findViewById(R.id.insomniabutton);
        completebutton = findViewById(R.id.completebutton);

        stressbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonClick(stressbutton);

            }
        });

        fearbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonClick(fearbutton);

            }
        });

        insomniabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(insomniabutton);
            }
        });

        completebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choose1 = stressbutton.getText().toString();
                String choose2 = fearbutton.getText().toString();
                String choose3 = insomniabutton.getText().toString();

                Log.d(TAG,"선택한 버튼1 내용 = "+ choose1);
                Log.d(TAG,"선택한 버튼2 내용 = "+ choose2);
                Log.d(TAG,"선택한 버튼3 내용 = "+ choose3);

                Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
    private void buttonClick(android.widget.Button button ){

        button.setBackgroundResource(R.drawable.buttonselected);
        button.setTextColor(getApplication().getResources().getColor(R.color.white));

    }
}
