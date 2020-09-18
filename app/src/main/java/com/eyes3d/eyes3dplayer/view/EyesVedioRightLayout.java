package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.AnimationUtils;

/**
 * Shengde·Cen on 2020/9/11
 * 说明：
 */
public class EyesVedioRightLayout extends FloatView {


    public EyesVedioRightLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mShowAnimation = AnimationUtils.getTranslateAnimation(1, 0, 0, 0,600);
        mDismissAnimation = AnimationUtils.getTranslateAnimation(0, 1, 0, 0,600);
    }

    @Override
    protected int retRootLayout() {
        return R.layout.vedio_right_layout;
    }

    @Override
    protected void initView() {

    }
}
