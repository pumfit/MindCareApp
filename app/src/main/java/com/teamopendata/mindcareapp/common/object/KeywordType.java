package com.teamopendata.mindcareapp.common.object;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.Utils;

import java.util.Locale;

public enum KeywordType {
    SYMPTOM("징후", R.array.keywords_symptom),
    STATE("상태", R.array.keywords_state),
    FACILITY("시설", R.array.keywords_facility),
    ADDICTION("중독", R.array.keywords_addiction);

    private final String korean;
    private final int resId;

    KeywordType(String korean, int resId) {
        this.korean = korean;
        this.resId = resId;
    }

    public String toKoran() {
        return korean;
    }

    public String toEnglish() {
        return Utils.toUpperCaseFirst(name());
    }

    public String toLowerCase() {
        return name().toLowerCase(Locale.ROOT);
    }

    public int resId() {
        return resId;
    }

}
