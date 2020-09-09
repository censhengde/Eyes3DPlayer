package com.eyes3d.eyes3dplayer;

import android.view.Surface;
import android.view.SurfaceHolder;

import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;

import java.util.Map;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：
 */
public interface IPlayerEngine extends PlayerController{
    void setDataSource(String path);
    void setDataSource(String path, Map<String,String> headers);
    void prepareAsync();
    void release();



    void setSurface(Surface surface);

    void setDisplay(SurfaceHolder holder);
}
