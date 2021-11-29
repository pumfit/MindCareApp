package com.teamopendata.mindcareapp;

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
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.common.SharedPreferencesManager;
import com.teamopendata.mindcareapp.databinding.ActivityUserGuideBinding;
import com.teamopendata.mindcareapp.databinding.ItemUserGuideBinding;

import java.util.Arrays;
import java.util.List;

public class UserGuideActivity extends AppCompatActivity {
    private final String TAG = "UserGuideActivity";
    ActivityUserGuideBinding binding;

    UserGuideAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserGuideBinding.inflate(getLayoutInflater());

        fullScreen();
        //if (SharedPreferencesManager.isFirstTimeLaunch(this)) showKeywordDialog();
        setContentView(binding.getRoot());

        adapter = new UserGuideAdapter();
        binding.vpUserGuide.setAdapter(adapter);
        binding.indicatorUserGuide.setViewPager2(binding.vpUserGuide);
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
        startActivity(new Intent(UserGuideActivity.this, SelectActivity.class));
        finish();
    }

    class UserGuideAdapter extends RecyclerView.Adapter<UserGuideAdapter.GuideViewHolder> {
        List<Integer> items = Arrays.asList(
                R.drawable.image_guide1,
                R.drawable.image_guide2,
                R.drawable.image_guide3,
                R.drawable.image_guide4
        );

        @NonNull
        @Override
        public UserGuideAdapter.GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GuideViewHolder(
                    ItemUserGuideBinding.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_guide, parent, false))
            );
        }

        @Override
        public void onBindViewHolder(@NonNull UserGuideAdapter.GuideViewHolder holder, int position) {
            holder.bind(items.get(position), position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class GuideViewHolder extends RecyclerView.ViewHolder {
            ItemUserGuideBinding mBinding;

            public GuideViewHolder(ItemUserGuideBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void bind(int viewId, int position) {
                Bitmap image = BitmapFactory.decodeResource(getResources(), viewId);
                mBinding.ivUserGuide.setImageBitmap(image);
                if (position == 0) {
                    binding.btnGuideBackOrSkip.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_skip));
                } else if (position == items.size() - 1) {
                    binding.btnGuideNextOrHome.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_home_base_color));
                } else {
                    binding.btnGuideBackOrSkip.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_next_36));
                    binding.btnGuideNextOrHome.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_back_36));
                }
                binding.btnGuideBackOrSkip.setOnClickListener(v -> {
                    Log.d(TAG, "bind: back");
                });

                binding.btnGuideNextOrHome.setOnClickListener(v -> {
                    Log.d(TAG, "bind: next");
                });
            }
        }
    }

}
