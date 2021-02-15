package com.hfad.ad2noteapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }

    public boolean isShown(){
        return preferences.getBoolean("isShown", false);
    }

    public void saveBoardsStatus() {
        preferences.edit().putBoolean("isShown", true).apply();
    }


    public void clearS(){
        preferences.edit().clear().apply();
        // preferences.edit().remove("isShown").apply();
    }
}