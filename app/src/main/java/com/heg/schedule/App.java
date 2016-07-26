package com.heg.schedule;

import android.app.Application;

import com.heg.baselib.utils.LogCat;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by hegang on 16/7/14.
 */
public class App extends Application {
    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        LogCat.setTag("hegang");
        mApp = this;
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public static App getApp(){
        return mApp;
    }
}
