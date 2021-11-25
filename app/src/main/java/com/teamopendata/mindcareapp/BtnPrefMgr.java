package com.teamopendata.mindcareapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class BtnPrefMgr {
    public  static final  String BTN_PREF_KEY = "btn_key";
    public  static void setStringArrayPref(Context context, String key, ArrayList<String> btnList){
        SharedPreferences prefs = context.getSharedPreferences(BTN_PREF_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i< btnList.size();i++){
            jsonArray.put(btnList.get(i));
        }
        if(!btnList.isEmpty()){
            editor.putString(key,String.valueOf(jsonArray));
        }
        else {
            editor.putString(key,null);
        }
        editor.apply();
    }

    public  static ArrayList<String> getStringArrayPref(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(BTN_PREF_KEY,Context.MODE_PRIVATE);
        String json = prefs.getString(key,String.valueOf(1));
        ArrayList<String> urls = new ArrayList<>();

        if(json != null){
            try{
                JSONArray a = new JSONArray(json);
                for(int i = 0;i<a.length();i++){
                    String url = a.optString(i);
                    urls.add(url);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        return urls;
    }
}