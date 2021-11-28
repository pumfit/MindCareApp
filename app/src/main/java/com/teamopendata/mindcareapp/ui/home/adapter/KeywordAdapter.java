package com.teamopendata.mindcareapp.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.ItemHomeKeywordsBinding;

import java.util.List;

public class KeywordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<String> mItems;

    public KeywordAdapter(List<String> item) {
        mItems = item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KeywordViewHolder(ItemHomeKeywordsBinding
                .bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_keywords, parent, false))
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((KeywordViewHolder) holder).bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class KeywordViewHolder extends RecyclerView.ViewHolder {
        ItemHomeKeywordsBinding mBinding;

        public KeywordViewHolder(ItemHomeKeywordsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(String keyword) {
            mBinding.tvHomeItemKeyword.setText(keyword);
        }
    }
}