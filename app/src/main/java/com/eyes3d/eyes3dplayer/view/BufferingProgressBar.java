package com.eyes3d.eyes3dplayer.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：缓冲进度条抽象，具体样式由子类定制
 */
public class BufferingProgressBar extends FloatView {

    public BufferingProgressBar(Context context,@LayoutRes int layoutResID) {
        super(context,layoutResID);
    }


}
