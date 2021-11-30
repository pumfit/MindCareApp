package com.teamopendata.mindcareapp.ui.keyword;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.object.ItemType;
import com.teamopendata.mindcareapp.common.object.Keyword;
import com.teamopendata.mindcareapp.common.object.KeywordType;
import com.teamopendata.mindcareapp.databinding.ItemKeywordContainerBinding;
import com.teamopendata.mindcareapp.databinding.ItemKeywordSaveBinding;
import com.teamopendata.mindcareapp.ui.keyword.model.KeywordItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @implNote private inner class KeywordContainerAdapter
 * protected inner class KeywordChipViewHolder
 * RecyclerView<ChipGroup<Chips>>
 */
public class KeywordContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final String TAG = "KeywordContainerAdapter";
    List<KeywordItem> mItems;
    WeakReference<Context> mContext;

    public KeywordContainerAdapter(Context context) {
        mContext = new WeakReference<>(context);
        initKeywords();
    }

    public KeywordContainerAdapter(List<KeywordItem> mItems) {
        this.mItems = mItems;
    }

    private void initKeywords() {
        mItems = new ArrayList<>();
        addKeyword(KeywordType.SYMPTOM);
        addKeyword(KeywordType.STATE);
        addKeyword(KeywordType.FACILITY);
        addKeyword(KeywordType.ADDICTION);
        mItems.add(new KeywordItem(ItemType.TYPE_BOTTOM));
    }

    private void addKeyword(KeywordType keywordType) {
        String[] keywords = mContext.get().getResources().getStringArray(keywordType.resId());
        mItems.add(new KeywordItem(keywordType.toEnglish(), Arrays.asList(keywords)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemType.TYPE_ITEM.ordinal()) {
            return new KeywordChipViewHolder(
                    ItemKeywordContainerBinding.bind(
                            LayoutInflater.from(parent.getContext()).inflate(
                                    R.layout.item_keyword_container, parent, false
                            )
                    )
            );
        } else {
            return new KeywordBottomViewHolder(
                    ItemKeywordSaveBinding.bind(
                            LayoutInflater.from(parent.getContext()).inflate(
                                    R.layout.item_keyword_save, parent, false
                            )
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KeywordChipViewHolder) {
            ((KeywordChipViewHolder) holder).bind(mItems.get(position));
        } else {
            ((KeywordBottomViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType().ordinal();
    }


    protected class KeywordChipViewHolder extends RecyclerView.ViewHolder {
        private final ItemKeywordContainerBinding mBinding;

        public KeywordChipViewHolder(@NonNull ItemKeywordContainerBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(KeywordItem item) {
            mBinding.tvKeywordHeader.setText(item.getHeader());
            for (Keyword keyword : item.getKeywords()) {
                mBinding.cgKeywordContainer.addView(newKeywordChip(keyword));
            }
        }

        private View newKeywordChip(Keyword keyword) {
            Chip chip = (Chip) LayoutInflater.from(mBinding.getRoot().getContext()).inflate(R.layout.item_chip, mBinding.cgKeywordContainer, false);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> Log.d(TAG, "newKeywordChip: " + buttonView.getText() + " isChecked->" + isChecked));
            chip.setText(keyword.toKorean());
            return chip;
        }
    }

    protected class KeywordBottomViewHolder extends RecyclerView.ViewHolder {
        private final ItemKeywordSaveBinding mBinding;

        public KeywordBottomViewHolder(@NonNull ItemKeywordSaveBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind() {
            mBinding.btnKeywordSave.setOnClickListener(v -> Log.d(TAG, "saveClick"));
        }
    }
}