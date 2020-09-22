package com.eyes3d.eyes3dplayer.view;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：缓冲进度条抽象，具体样式由子类定制
 */
public abstract class BufferingProgressBar extends FloatView {

    public BufferingProgressBar(Context context) {
        this(context,null);
    }

    public BufferingProgressBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPlayerPrepared(PlayerController controller) {
        if (this.isShowing()) {
            this.dismiss();
        }
    }
}
