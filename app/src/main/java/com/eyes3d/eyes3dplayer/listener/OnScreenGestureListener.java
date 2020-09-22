package com.eyes3d.eyes3dplayer.listener;

import android.view.MotionEvent;

/**
 * Shengde·Cen on 2020/9/14
 * 说明：
 */
public interface OnScreenGestureListener {
    //亮度手势，手指在Layout左半部上下滑动时候调用
    public void onBrightnessGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    //音量手势，手指在Layout右半部上下滑动时候调用
    public void onVolumeGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    //快进快退手势，手指在Layout左右滑动的时候调用
    public void onHorizontalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    //单击手势，确认是单击的时候调用
    public void onSingleTapConfirmed(MotionEvent e);
    //双击手势，确认是双击的时候调用
    public void onDoubleTap(MotionEvent e);
    //按下手势，第一根手指按下时候调用
    public void onDown(MotionEvent e);
    //快进快退执行后的松开时候调用
    public void onFF_REWUp(MotionEvent e);
}
