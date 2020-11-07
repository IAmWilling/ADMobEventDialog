package com.zhy.admobeventdialog;

import android.app.Application;

import com.admob.admobevwindow.ADMobEv;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ADMobEv.getInstance().init(this,null);
    }
}
