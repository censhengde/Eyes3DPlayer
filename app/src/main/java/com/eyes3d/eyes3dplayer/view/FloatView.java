package com.eyes3d.eyes3dplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.PlayerController;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：浮动视图抽象
 */
 abstract class FloatView extends FrameLayout {


    protected abstract @LayoutRes
    int retRootLayout();

    private boolean mShowing = false;

    public boolean isOnTouching() {
        return mIsOnTouching;
    }

    //
    private boolean mIsOnTouching = false;


    /*隐藏动画*/
    protected Animation mDismissAnimation;
    /*显示动画*/
    protected Animation mShowAnimation;
    /*判断当前是否有动画正在执行*/
    protected int mAnimationCounts = 0;

    public FloatView(Context context) {
        this(context, null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //内部也是调用LayoutInfate.from.....且 attachToRoot=true，意味者直接进行addView（）
        //当Inflate以< merge />开头的布局文件时，必须指定一个父ViewGroup，并且必须设定attachToRoot为true
        // （参看inflate(int, android.view.ViewGroup, Boolean)方法）。 必须为TRUE，是因为MERGE标签里没有可用的根结点
        View.inflate(this.getContext(), retRootLayout(), this);
        this.setVisibility(GONE);/*默认不可见*/
        setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsOnTouching = true;
                    break;
                case MotionEvent.ACTION_UP:
                    mIsOnTouching = false;
                    break;
                default:
                    break;
            }
            return false;
        });
        initView();
    }

    protected void retRootLayout(@LayoutRes int layoutID) {
        View.inflate(this.getContext(), layoutID, this);
    }

    protected abstract void initView();

    public void onPlayerPrepared(PlayerController controller) {

    }

    public void show() {
        mShowing = true;
        if (getVisibility() == VISIBLE) return;
        if (mShowAnimation == null) {
            Log.e("", "mShowAnimation 为null");
            this.setVisibility(View.VISIBLE);
            return;
        }
        if (mAnimationCounts > 0) {
            return;
        }
        FloatView.this.setVisibility(View.VISIBLE);/**/
        this.startAnimation(mShowAnimation);
        mShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                FloatView.this.mAnimationCounts++;
                //FloatView.this.setVisibility(View.VISIBLE);不能把这句放到这里，因为动画是View可见才能执行
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FloatView.this.mAnimationCounts--;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public boolean isShowing() {
        return mShowing;
    }

    public void dismiss() {
        mShowing = false;
        if (getVisibility() == GONE) return;
        if (mDismissAnimation == null) {
            Log.e("", "mDismissAnimation 为null");
            FloatView.this.setVisibility(View.GONE);
            return;
        }
        if (mAnimationCounts > 0) {
            return;
        }
        this.startAnimation(mDismissAnimation);
        mDismissAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                FloatView.this.mAnimationCounts++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FloatView.this.mAnimationCounts--;
                if (FloatView.this.getVisibility() == VISIBLE) {
                    FloatView.this.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


}
