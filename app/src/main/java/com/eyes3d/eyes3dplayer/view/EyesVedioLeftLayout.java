package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.listener.OnClickVedioLeftLayoutListener;
import com.eyes3d.eyes3dplayer.utils.AnimationUtils;

/**
 * Shengde·Cen on 2020/9/11
 * 说明：
 */
public class EyesVedioLeftLayout extends FloatView {
    private Button mBtnLock;
    private boolean mLocked = false;
    private OnClickVedioLeftLayoutListener mListener;

    public void setListener(OnClickVedioLeftLayoutListener listener) {
        mListener = listener;
    }

    public EyesVedioLeftLayout(Context context) {
        super(context);
    }

    public EyesVedioLeftLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mShowAnimation = AnimationUtils.getTranslateAnimation(-1, 0, 0, 0, 600);
        mDismissAnimation = AnimationUtils.getTranslateAnimation(0, -1, 0, 0, 600);
    }

    @Override
    protected int retRootLayout() {
        return R.layout.vedio_left_layout;
    }

    @Override
    protected void initView() {
        mBtnLock = findViewById(R.id.btn_vedio_left_layout_lock);
        mBtnLock.setOnClickListener((v) -> {
            if (mListener == null) return;
            if (!mLocked) {
                mLocked = true;
                mListener.onLock();
                mBtnLock.setBackgroundResource(R.mipmap.btn_bg_lock);
            } else {
                mLocked = false;
                mListener.onUnLock();
                mBtnLock.setBackgroundResource(R.mipmap.btn_bg_unlock);
            }

        });
    }
}
