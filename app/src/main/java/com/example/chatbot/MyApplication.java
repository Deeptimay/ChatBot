package com.example.chatbot;

import androidx.multidex.MultiDexApplication;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends MultiDexApplication {

    private static MyApplication singleton;

    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
