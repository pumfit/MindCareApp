package com.teamopendata.mindcareapp.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.teamopendata.mindcareapp.MainActivity;
import com.teamopendata.mindcareapp.R;

public class IntroActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_intro);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(IntroActivity.this, UserGuideActivity.class);
            startActivity(intent);
            finish();
        }, 800);
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
