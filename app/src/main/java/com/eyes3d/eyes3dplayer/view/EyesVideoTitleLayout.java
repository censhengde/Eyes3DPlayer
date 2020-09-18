package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.AnimationUtils;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：播放界面标题栏布局
 */
public class EyesVideoTitleLayout extends FloatView {
    private Button mBtnBack;

    public EyesVideoTitleLayout(Context context) {
        super(context);
    }

    public EyesVideoTitleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDismissAnimation = AnimationUtils.getTranslateAnimation(0, 0, 0, -1,600);
        mShowAnimation = AnimationUtils.getTranslateAnimation(0, 0, -1, 0,600);
    }

    @Override
    protected int retRootLayout() {
        return R.layout.vedio_title_layout;
    }

    @Override
    protected void initView() {
        mBtnBack = findViewById(R.id.btn_vedio_title_layout_back);
    }


}
