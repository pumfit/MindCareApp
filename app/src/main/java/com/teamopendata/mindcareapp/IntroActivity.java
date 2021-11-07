package com.teamopendata.mindcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                Intent intent = new Intent(IntroActivity.this,SelectActivity.class);
                startActivity(intent); finish();
            }
        },800);
    }


    @Override
    protected  void onPause(){
        super.onPause();
        finish();
    }
}
