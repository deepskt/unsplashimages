package com.deepak.mobikwikimage;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.deepak.mobikwikimage.manager.DBManager;
import com.deepak.mobikwikimage.util.Tracer;

public class MobikwikApplication extends MultiDexApplication {
    private static final String TAG = Config.logger + MobikwikApplication.class.getSimpleName();
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Tracer.debug(TAG," onCreate "+" ");
        sAppContext = getApplicationContext();
        DBManager.init();
    }

    public static Context getsAppContext(){
        return sAppContext;
    }
}
