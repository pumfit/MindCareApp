package com.teamopendata.mindcareapp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.teamopendata.mindcareapp.R;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public void setContentView(int layoutResID) {
            LinearLayout baseLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
            FrameLayout layoutContainer = baseLayout.findViewById(R.id.container_base);
            getLayoutInflater().inflate(layoutResID, layoutContainer, true);
            super.setContentView(baseLayout);

            initToolbar();
    }

    protected boolean useToolbar() {
        return true;
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);

        if (useToolbar()) {
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();

            setToolbarTitle("");
            displayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back_border);
        }
    }

    public void setToolbarTitle(String title) {
        actionBar.setTitle(title);
    }

    protected void hideActionBar() {
        this.actionBar.hide();
    }

    public void showActionBar() {
        this.actionBar.show();
    }


    public void displayHomeAsUpEnabled(boolean isVisible) {
        actionBar.setDisplayHomeAsUpEnabled(isVisible);
    }

    public void displayActionBarUndabled() {
        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // 일단 프로토타입이니까 이렇게 하고 프레그먼트 백 스택은 콜백으로 처리해야할듯 합니다.
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_setting:
                Toast.makeText(this, "setting clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}