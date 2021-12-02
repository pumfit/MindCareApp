package com.teamopendata.mindcareapp.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teamopendata.mindcareapp.common.model.entity.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class SharedPreferencesManager {
    private static final String PREF_NAME = "mind_charge_prefs";

    /**
     * SharedPreferences Keys
     */
    private enum Key {
        FIRST_TIME_LAUNCH("first_time_launch"),
        USER_KEYWORDS("user_keywords");

        private final String key;

        Key(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    private SharedPreferencesManager() {
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 앱의 처음 실행 여부 설정
     *
     * @param isFirstTime 앱 처음 실행 여부
     * @key Key.FIRST_TIME_LAUNCH
     */
    public static void setFirstTimeLaunch(Context context, boolean isFirstTime) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Key.FIRST_TIME_LAUNCH.getKey(), isFirstTime);
        editor.apply();
    }

    /**
     * 앱이 처음 실행되었는지 여부
     *
     * @return true or false
     * @key Key.FIRST_TIME_LAUNCH
     */
    public static boolean isFirstTimeLaunch(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getBoolean(Key.FIRST_TIME_LAUNCH.getKey(), true);
    }

    /**
     * 키워드 저장
     * convert: ArrayList -> Json
     *
     * @param keywords {@link com.teamopendata.mindcareapp.common.object.Keyword}
     * @key Key.USER_KEYWORDS
     */
    public static void saveUserKeywords(Context context, List<String> keywords) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        String keywordsJson = new Gson().toJson(keywords);
        editor.putString(Key.USER_KEYWORDS.getKey(), keywordsJson);
        editor.apply();
    }

    /**
     * 키워드 가져오기
     * convert: Json -> ArrayList
     *
     * @return keywordsArrayList
     * @key Key.USER_KEYWORDS
     */
    public static ArrayList<String> getUserKeywords(Context context) {
        SharedPreferences prefs = getPreferences(context);
        String keywords = prefs.getString(Key.USER_KEYWORDS.getKey(), null);
        Type type = new TypeToken<List<String>>() {
        }.getType();

        return keywords == null ? null : new Gson().fromJson(keywords, type);
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
