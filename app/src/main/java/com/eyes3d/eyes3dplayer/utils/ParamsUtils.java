package com.eyes3d.eyes3dplayer.utils;

/**
 * Shengde·Cen on 2020/9/9
 * 说明：
 */
public final class ParamsUtils {
    private ParamsUtils(){}

    public static void checkNotNull(Object arg, String throwMsg) {
        if (arg == null) {
            throw new RuntimeException(throwMsg);
        }
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
