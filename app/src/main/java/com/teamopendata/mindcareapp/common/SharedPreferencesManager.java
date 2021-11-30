package com.teamopendata.mindcareapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public abstract class SharedPreferencesManager {

    // Shared preferences file name
    private static final String PREF_NAME = "mind_charge_prefs";

    private static final String FIRST_TIME_LAUNCH = "first_time_launch";

    private SharedPreferencesManager() {
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    /**
     * 앱의 처음 실행 여부 설정
     *
     * @param isFirstTime 앱 처음 실행 여부
     */
    public static void setFirstTimeLaunch(Context context, boolean isFirstTime) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    /**
     * 앱이 처음 실행되었는지 여부
     *
     * @return true or false
     */
    public static boolean isFirstTimeLaunch(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getBoolean(FIRST_TIME_LAUNCH, true);
    }


    /**
     * 모든 데이터 삭제
     */
    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();
    }
}
