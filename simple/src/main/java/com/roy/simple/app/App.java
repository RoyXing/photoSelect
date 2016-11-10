package com.roy.simple.app;

import android.app.Application;

import com.xzy.roy.photoselect.utils.InitGallery;

/**
 * Created by roy on 2016/11/7.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InitGallery.getInstance().initGallery(getApplicationContext());
    }
}
