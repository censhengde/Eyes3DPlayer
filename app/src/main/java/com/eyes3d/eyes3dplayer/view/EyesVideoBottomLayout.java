package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.view.EyesProgressBar;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：
 */
public class EyesVideoBottomLayout extends FloatView {
    /*播放进度条*/
    private EyesProgressBar mProgressBar;

    public EyesVideoBottomLayout(Context context) {
        super(context);
    }

    public EyesVideoBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setContentView() {
        return R.layout.vedio_bottom_layout;
    }

    @Override
    protected void initView() {

    }
}
