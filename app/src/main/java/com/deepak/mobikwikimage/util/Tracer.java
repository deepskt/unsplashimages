package com.deepak.mobikwikimage.util;

import android.util.Log;

import com.deepak.mobikwikimage.Config;

public class Tracer {

    private static final String TAG_ = Config.logger + Tracer.class.getSimpleName();

    public static void debug(String TAG, String message) {
        Log.d(TAG, message);
    }
}
