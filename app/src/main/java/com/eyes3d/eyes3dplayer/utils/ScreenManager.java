package com.eyes3d.eyes3dplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * Shengde·Cen on 2020/9/14
 * 说明：
 */
public final class ScreenManager {
    private ScreenManager() {
    }

    public static void setScreenBringhtness(Activity activity, float value) {
        if (activity==null||value<0){
            return;
        }

        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = value;
        activity.getWindow().setAttributes(lp);
    }
public static  int getScreenBrightness(Context context){
    int screenBrightness = 255;
    try {
        screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
    } catch (Exception localException) {
        localException.printStackTrace();
    }
    return screenBrightness;
}
}
