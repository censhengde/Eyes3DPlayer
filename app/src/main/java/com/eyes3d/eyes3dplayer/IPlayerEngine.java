package com.eyes3d.eyes3dplayer;

import android.view.Surface;
import android.view.SurfaceHolder;

import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：
 */
public interface IPlayerEngine {
    void setDataSource(String path);

    void prepareAsync();

    void start();

    void pause();

    void stop();

    void reset();

    void release();

    boolean isPlaying();

    int getCurrentPosition();

    int getDuration();

    void seekTo(int msec) throws IllegalStateException;

    void setSurface(Surface surface);

    void setDisplay(SurfaceHolder holder);
}
