package com.twtstudio.tjwhm.lostfound;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.twt.wepeiyang.commons.utils.App;

/**
 * Created by tjwhm on 2017/7/5.
 **/

public class lostfoundApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(getApplicationContext()).build();
    }
}
