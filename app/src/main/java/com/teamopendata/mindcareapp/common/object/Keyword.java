package com.teamopendata.mindcareapp.common.object;

import android.util.Log;

public enum Keyword {
    /**
     * @see com.teamopendata.mindcareapp.R.array
     * keywords_symptom
     */

    DEPRESS_ANXIETY("우울 · 불안"),
    SLEEPLESSNESS("불면"),
    STRESS("스트레스"),
    PHOBIA("공포증"),
    MENTAL_ILLNESS("정신질환"),
    SUICIDAL_THOUGHTS("자살생각"),
    SUICIDE_ATTEMPT("자해 · 자살시도"),
    MENTAL_RETARDATION("정신지체"),

    /**
     * @see com.teamopendata.mindcareapp.R.array
     * keywords_state
     */
    ADULT("성인"),
    CHILDREN_AND_YOUTH("아동 · 청소년"),
    OLD_MAN_DEMENTIA("노인 · 치매"),
    PARENTS_FAMILY("부모 · 가족"),
    SURVIVORS_OF_SUICIDE("자살유족"),
    HEALTHCARE("건강관리"),
    CALAMITY("재난"),

    /**
     * @see com.teamopendata.mindcareapp.R.array
     * keywords_facility
     */
    OUTPATIENT_HOSPITALIZATION("외래 · 입원(입소)"),
    REHABILITATION("재활"),
    DWELLING("주거"),
    LIFE_LEAD("생활지도"),
    VOCATIONAL_EDUCATION("직업교육"),
    RETURN_SOCIETY_AND_ADAPTATION("사회복귀 · 적응"),

    /**
     * @see com.teamopendata.mindcareapp.R.array
     * keywords_addiction
     */
    ALCOHOLISM("알코올 중독"),
    GAMBLING_ADDICTION("도박 중독"),
    DRUG_ADDICTION("약물(마약) 중독"),
    INTERNET_SMARTPHONE_ADDICTION("인터넷(스마트폰) 중독");

    private final String korean;

    Keyword(String korean) {
        this.korean = korean;
    }

    public String toKorean() {
        return korean;
    }

    public static Keyword findBy(String s) {
        Log.d("Keyword", "findBy: "+s);
        for (Keyword value : values()) {
            if (value.korean.equals(s)) return value;
        }
        return null;
    }

}
