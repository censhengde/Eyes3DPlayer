package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：浮动视图抽象
 */
abstract class FloatView{
    protected final FrameLayout mRootLayout;
    public FloatView(Context context,@LayoutRes int layoutResID) {
        mRootLayout = new FrameLayout(context);
        //内部也是调用LayoutInfate.from.....且 attachToRoot=true，意味者直接进行addView（）
        //当Inflate以< merge />开头的布局文件时，必须指定一个父ViewGroup，并且必须设定attachToRoot为true
        // （参看inflate(int, android.view.ViewGroup, Boolean)方法）。 必须为TRUE，是因为MERGE标签里没有可用的根结点
        View root = View.inflate(context, layoutResID, mRootLayout);


    }

    public FrameLayout getLayout() {
        return mRootLayout;
    }

    public final <T extends View> T findViewById(int id){
        return  mRootLayout.findViewById(id);
    }

    protected boolean isShowing = false;

    public  void show(){
        mRootLayout.setVisibility(View.VISIBLE);
    }

    public  void dismiss(){
        mRootLayout.setVisibility(View.GONE);
    }
}
