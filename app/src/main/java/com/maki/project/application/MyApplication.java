package com.maki.project.application;

import android.app.Application;

import butterknife.ButterKnife;

/**
 * Administrator on 2016/7/21.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
    }
}
