package com.teamopendata.mindcareapp.ui.intro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.SharedPreferencesManager;
import com.teamopendata.mindcareapp.databinding.ActivityUserGuideBinding;
import com.teamopendata.mindcareapp.databinding.ItemUserGuideBinding;
import com.teamopendata.mindcareapp.ui.MainActivity;

import java.util.Arrays;
import java.util.List;

public class UserGuideActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "UserGuideActivity";
    private ActivityUserGuideBinding binding;

    private UserGuideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();

        binding = ActivityUserGuideBinding.inflate(getLayoutInflater());

        if (!SharedPreferencesManager.isFirstTimeLaunch(this)) showKeywordDialog();
        setContentView(binding.getRoot());

        adapter = new UserGuideAdapter();
        binding.vpUserGuide.setAdapter(adapter);
        binding.indicatorUserGuide.setViewPager2(binding.vpUserGuide);

        binding.btnGuideBackOrSkip.setOnClickListener(this);
        binding.btnGuideNextOrHome.setOnClickListener(this);

        binding.vpUserGuide.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);

                if (adapter.isFirstItem(position)) {
                    binding.btnGuideBackOrSkip.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_skip));
                } else if (adapter.isLastItem(position)) {
                    binding.btnGuideNextOrHome.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_home_border));
                } else {
                    binding.btnGuideNextOrHome.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_next_32));
                    binding.btnGuideBackOrSkip.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_back_32));
                }
                super.onPageSelected(position);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int currentPosition = binding.vpUserGuide.getCurrentItem();

        switch (v.getId()) {
            case R.id.btn_guide_back_or_skip:
                if (adapter.isFirstItem(currentPosition)) showKeywordDialog();
                else binding.vpUserGuide.setCurrentItem(currentPosition - 1);
                break;
            case R.id.btn_guide_next_or_home:
                if (adapter.isLastItem(currentPosition)) showKeywordDialog();
                else binding.vpUserGuide.setCurrentItem(currentPosition + 1);
                break;

        }
    }

    public void fullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showKeywordDialog() {
        SharedPreferencesManager.setFirstTimeLaunch(this, false);
        startActivity(new Intent(UserGuideActivity.this, MainActivity.class));
        finish();
    }


    /**
     * private inner class UserGuideAdapter
     * private inner class UserGuideViewHolder
     */
    private class UserGuideAdapter extends RecyclerView.Adapter<UserGuideAdapter.UserGuideViewHolder> {
        List<Integer> items = Arrays.asList(
                R.drawable.image_guide1,
                R.drawable.image_guide2,
                R.drawable.image_guide3,
                R.drawable.image_guide4,
                R.drawable.image_guide5
        );

        @NonNull
        @Override
        public UserGuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserGuideViewHolder(
                    ItemUserGuideBinding.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_guide, parent, false))
            );
        }

        @Override
        public void onBindViewHolder(@NonNull UserGuideViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder: " + position);
            holder.bind(items.get(position), position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public boolean isFirstItem(int position) {
            return position == 0;
        }

        public boolean isLastItem(int position) {
            return position == items.size() - 1;
        }

        private class UserGuideViewHolder extends RecyclerView.ViewHolder {
            private final ItemUserGuideBinding mBinding;

            private UserGuideViewHolder(ItemUserGuideBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            private void bind(int viewId, int position) {
                Log.d(TAG, "bind: " + position);

                Bitmap image = BitmapFactory.decodeResource(getResources(), viewId);
                mBinding.ivUserGuide.setImageBitmap(image);
            }
        }
    }

}
