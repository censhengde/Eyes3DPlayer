package com.eyes3d.eyes3dplayer.listener;

import android.view.MotionEvent;

/**
 * Shengde·Cen on 2020/9/14
 * 说明：
 */
public interface OnScreenGestureListener {
    //单击
    void onSingleTapConfirmed(MotionEvent e);
    //双击
    void onDoubleTap(MotionEvent e);

    void onHorizontalScroll(MotionEvent e);
    void onHorizontalScrollUp(MotionEvent e);
    void onVerticalScroll(MotionEvent e);
    void onVerticalScrollUp(MotionEvent e);
//    //左滑
//    void onLeftFling(MotionEvent e);
//    //右滑
//    void onRightFling(MotionEvent e);
//    //上滑
//    void onUpFling(MotionEvent e);
//   //下滑
//    void onDownFling(MotionEvent e);
}
