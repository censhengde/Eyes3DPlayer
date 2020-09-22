package com.eyes3d.eyes3dplayer.utils;

/**
 * Shengde·Cen on 2020/9/22
 * 说明：
 */
public final class FormatUtils {
    private FormatUtils() {
    }
    public static String mssToString(long mss){
            int sec = (int) (mss / 1000) % 60;
            int min = (int) ((mss / 1000) / 60) % 60;
            int hour = (int) ((mss / 1000) / 60) / 60;
            return (hour <= 9 ? "0" + hour : hour) + ":" + (min <= 9 ? "0" + min : min) + ":" + (sec <= 9 ? "0" + sec :
                    sec);
    }

}
