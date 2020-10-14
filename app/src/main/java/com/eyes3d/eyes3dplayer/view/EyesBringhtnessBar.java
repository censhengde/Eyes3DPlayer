package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：屏幕亮度调节条
 */
public class EyesBringhtnessBar extends EyesVolumeBar{
    @Override
    protected int retIconID() {
        return R.mipmap.ic_bringhtness;
    }

    public EyesBringhtnessBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
