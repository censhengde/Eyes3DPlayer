package com.eyes3d.eyes3dplayer.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Shengde·Cen on 2020/9/14
 * 说明：
 */
public final class EyesLog {
    private EyesLog() {
    }

    private static boolean DEBUG = true;

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void e(Object target, String msg) {
        if (DEBUG && !TextUtils.isEmpty(msg)&&target!=null) {
            Log.e(target.getClass().getSimpleName(), msg);
        }
    }
}
