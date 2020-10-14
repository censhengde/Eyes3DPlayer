package com.eyes3d.eyes3dplayer.impl;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.IntDef;

import com.eyes3d.eyes3dplayer.listener.OnScreenGestureListener;
import com.eyes3d.eyes3dplayer.utils.EyesLog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Shengde·Cen on 2020/9/21
 * 说明：
 */
public final class OnVideoScreenGestureListener extends GestureDetector.SimpleOnGestureListener {
    private OnScreenGestureListener mPlayerOnGestureListener;
    private static final int NONE = 0;//VOLUME,BRIGHTNESS,FF_REW
    private static final int VOLUME = 1;//VOLUME,BRIGHTNESS,FF_REW
    private static final int BRIGHTNESS = 2;//VOLUME,BRIGHTNESS,FF_REW
    private static final int FF_REW = 3;//VOLUME,BRIGHTNESS,FF_REW
    private int mParentWidth;
    private int mHeight;
    private int mCurrentPostion = 0;
    //横向偏移检测，让快进快退不那么敏感
    private static final int MIN_OFFSET = 1;
    public boolean mHasFFREW = false;
    @ScrollMode
    private int mScrollMode;

    @IntDef({NONE, VOLUME, BRIGHTNESS, FF_REW})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ScrollMode {
    }

    public void setParentWidth(int parentWidth) {
        mParentWidth = parentWidth;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public OnVideoScreenGestureListener(OnScreenGestureListener listener) {

        mPlayerOnGestureListener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mScrollMode = NONE;
        if (mPlayerOnGestureListener != null) {
            mPlayerOnGestureListener.onDown(e);
        }

        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        switch (mScrollMode) {
            case NONE:
                //offset是让快进快退不要那么敏感的值
                if (Math.abs(distanceX) - Math.abs(distanceY) > MIN_OFFSET) {
                    mScrollMode = FF_REW;
                } else {
                    if (mParentWidth > 0) {
                        if (e1.getX() < mParentWidth / 2.0) {
                            mScrollMode = BRIGHTNESS;
                        } else {
                            mScrollMode = VOLUME;
                        }
                    }
                }
                break;
            case VOLUME:
                if (mPlayerOnGestureListener != null) {
                    mPlayerOnGestureListener.onVolumeGesture(e1, e2, distanceX, distanceY);
                }
                break;
            case BRIGHTNESS:
                if (mPlayerOnGestureListener != null) {
                    mPlayerOnGestureListener.onBrightnessGesture(e1, e2, distanceX, distanceY);
                }
                break;
            case FF_REW:
                if (mPlayerOnGestureListener != null) {
                    mPlayerOnGestureListener.onHorizontalScroll(e1, e2, distanceX, distanceY);
                }
                mHasFFREW = true;
                break;
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        EyesLog.e(this, "onSingleTapConfirmed");
        mPlayerOnGestureListener.onSingleTapConfirmed(e);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        mPlayerOnGestureListener.onDoubleTap(e);
        return false;
    }
}
