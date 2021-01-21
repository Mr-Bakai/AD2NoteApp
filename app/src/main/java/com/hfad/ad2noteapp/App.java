package com.hfad.ad2noteapp;

import android.app.Application;

import androidx.room.Room;

import com.hfad.ad2noteapp.Room.AppDataBase;

public class App extends Application {

    private  static AppDataBase appDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDataBase = Room.databaseBuilder(
                this,
                AppDataBase.class,
                "database")
                .allowMainThreadQueries()
                .build();
    }

    public static AppDataBase getAppDataBase() {
        return appDataBase;
    }
}