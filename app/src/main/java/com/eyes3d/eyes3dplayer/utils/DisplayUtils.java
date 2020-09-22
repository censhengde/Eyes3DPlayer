package com.eyes3d.eyes3dplayer.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Shengde·Cen on 2020/9/3
 * 说明：
 */
public class DisplayUtils {

    public static Point getDisplayRealSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getRealSize(point);
        }
        return point;
    }


    public static int getStatusHeight(Context context){
       int res=context.getResources().getIdentifier("status_bar_height", "dimen", "android");
       return context.getResources().getDimensionPixelSize(res);
    }

}
