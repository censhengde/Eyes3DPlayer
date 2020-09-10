package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：浮动视图抽象
 */
abstract class FloatView extends FrameLayout {


    private int mNoTouchDuring = 3500;

    protected abstract @LayoutRes int setContentView();

    private Handler mHandler;

    /*隐藏动画*/

    public FloatView(Context context) {
        this(context, null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mHandler = new Handler(Looper.getMainLooper());
        //内部也是调用LayoutInfate.from.....且 attachToRoot=true，意味者直接进行addView（）
        //当Inflate以< merge />开头的布局文件时，必须指定一个父ViewGroup，并且必须设定attachToRoot为true
        // （参看inflate(int, android.view.ViewGroup, Boolean)方法）。 必须为TRUE，是因为MERGE标签里没有可用的根结点
        View.inflate(context, setContentView(), this);
        initView();
    }

    protected abstract void initView();

    protected boolean isShowing = false;

    public int getNoTouchDuring() {
        return mNoTouchDuring;
    }

    public void setNoTouchDuring(int noTouchDuring) {
        mNoTouchDuring = noTouchDuring;
    }

    public void show() {
        if (this.getVisibility() == GONE || getVisibility() == INVISIBLE) {
            this.setVisibility(View.VISIBLE);
        }
        postDismiss(getNoTouchDuring());
    }

    public void dismiss() {
        if (this.getVisibility() == VISIBLE) {
            this.setVisibility(View.GONE);
        }

    }

    public void postDismiss(int during) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, during);
    }
}
