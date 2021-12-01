package com.teamopendata.mindcareapp.ui.keyword;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.SharedPreferencesManager;
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
 * @implNote protected inner class {@link KeywordChipViewHolder}
 * protected inner class{@link KeywordBottomViewHolder}
 * RecyclerView<ChipGroup<Chips>> Adds chips dynamically to a chip group.
 */
public class KeywordContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final String TAG = "KeywordContainerAdapter";

    /**
     * 사용자가 선택할 수 있는 키워드 최대 갯수
     */
    public static final int MAX_CHOICE = 4;

    private int KEYWORD_TOTAL_NUMBER = 0;

    private List<KeywordItem> mItems;
    private WeakReference<Context> mContext;

    private List<String> cachedKeywords = new ArrayList<>(4);

    private final List<Chip> chips = new ArrayList<>();

    private Button saveButton;
    private final OnSaveFinishedListener mListener;

    public interface OnSaveFinishedListener {
        void onFinished(List<String> keywords);
    }

    public KeywordContainerAdapter(Context context, OnSaveFinishedListener listener) {
        mContext = new WeakReference<>(context);
        mListener = listener;
        initKeywords();
    }

    public KeywordContainerAdapter(Context context, List<String> keywords, OnSaveFinishedListener listener) {
        mContext = new WeakReference<>(context);
        cachedKeywords = keywords;
        mListener = listener;
        initKeywords();
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
        KEYWORD_TOTAL_NUMBER += keywords.length;
        mItems.add(new KeywordItem(keywordType.toEnglish(), Arrays.asList(keywords)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemType.TYPE_ITEM.ordinal()) {
            return new KeywordChipViewHolder(
                    ItemKeywordContainerBinding.bind(LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_keyword_container, parent, false)
                    )
            );
        } else {
            return new KeywordBottomViewHolder(
                    ItemKeywordSaveBinding.bind(LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_keyword_save, parent, false)
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

    private boolean isChoiceFinished() {
        return cachedKeywords.size() == MAX_CHOICE;
    }

    private boolean validationKeyword() {

        return false;
    }

    private void setAllCheckable(boolean checkable) {
        for (Chip chip : chips) {
            if (!chip.isChecked()) {
                chip.setCheckable(checkable);
            }
        }
    }

    private void updateView() {
        Log.d(TAG, "cachedKeywords.size: " + cachedKeywords.size());
        if (saveButton != null) {
            int textId = mContext.get().getResources().getIdentifier(
                    "keyword_choice_" + (cachedKeywords.size() != 0 ? cachedKeywords.size() : 0) + "_4", "string", mContext.get().getPackageName());

            String s = mContext.get().getResources().getString(textId);
            Log.d(TAG, "keywordUpdateView: " + s);
            saveButton.setText(s);

            //saveButton.setClickable(cachedKeywords.size() == MAX_CHOICE);
        }
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
                if (chips.size() == KEYWORD_TOTAL_NUMBER)
                    mBinding.cgKeywordContainer.addView(chips.get(keyword.ordinal()));
                mBinding.cgKeywordContainer.addView(newKeywordChip(keyword));
            }
        }

        private View newKeywordChip(Keyword keyword) {
            Log.d(TAG, "newKeywordChip: " + keyword.toKorean());
            Chip chip = (Chip) LayoutInflater.from(mBinding.getRoot().getContext()).inflate(R.layout.item_chip, mBinding.cgKeywordContainer, false);
            chip.setText(keyword.toKorean());

            if (cachedKeywords.contains(keyword.toKorean())) chip.setChecked(true);

            chip.setOnCheckedChangeListener(this::onCheckedChange);

            /*
            for (String cachedKeyword : cachedKeywords) {
                if (cachedKeyword.equals(keyword.toKorean())) chip.setChecked(true);
            }*/

            if (chips.size() != KEYWORD_TOTAL_NUMBER) chips.add(keyword.ordinal(), chip);
            if (isChoiceFinished()) setAllCheckable(false);
            return chip;
        }

        private void onCheckedChange(android.widget.CompoundButton buttonView, boolean isChecked) {
            Log.d(TAG, "newKeywordChip: " + buttonView.getText() + " isChecked->" + isChecked);

            if (isChecked) {
                cachedKeywords.add(buttonView.getText().toString());
            } else cachedKeywords.remove(buttonView.getText().toString());

            if (isChoiceFinished()) setAllCheckable(false);
            else setAllCheckable(true);

            updateView();
        }
    }

    protected class KeywordBottomViewHolder extends RecyclerView.ViewHolder {
        private final ItemKeywordSaveBinding mBinding;

        public KeywordBottomViewHolder(@NonNull ItemKeywordSaveBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind() {
            Log.d(TAG, "button: save");
            saveButton = mBinding.btnKeywordSave;
            updateView();

            mBinding.btnKeywordSave.setOnClickListener(v -> {
                for (String keyword : cachedKeywords) {
                    Log.d(TAG, "save: " + keyword);
                }
                SharedPreferencesManager.saveUserKeywords(mContext.get(), cachedKeywords);
                mListener.onFinished(cachedKeywords);
            });
        }


    }
}