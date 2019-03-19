package com.example.opengles.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class GLApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }

}
