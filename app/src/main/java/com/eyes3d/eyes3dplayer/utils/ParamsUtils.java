package com.eyes3d.eyes3dplayer.utils;

import android.util.Log;

/**
 * Shengde·Cen on 2020/9/9
 * 说明：
 */
public final class ParamsUtils {
    private ParamsUtils() {
    }

    public static void checkNotNull(Object arg, String throwMsg) {
        if (arg == null) {
            throw new RuntimeException(throwMsg);
        }
    }

    public static boolean checkParamIsNull(Object arg, String throwMsg) {
        if (arg == null) {
            Log.e("", throwMsg);
            return false;
        }
        return true;
    }

    public static void checkNotNull(String throwMsg, Object... args) {
        if (args != null) {
            for (Object arg : args) {
                if (arg == null) {
                    throw new RuntimeException(throwMsg);
                }
            }
        }
    }
}
