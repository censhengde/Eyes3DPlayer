package com.eyes3d.eyes3dplayer.utils;

import android.content.Context;

import androidx.annotation.DimenRes;

/**
 * Shengde·Cen on 2020/10/15
 * 说明：
 */
public final class DimenUtils {
    private DimenUtils() {
    }

    public static int getDimension(Context context, @DimenRes int id) {
        return (int) context.getResources().getDimension(id);
    }
}
