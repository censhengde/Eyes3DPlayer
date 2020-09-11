package com.eyes3d.eyes3dplayer.engine;

import androidx.annotation.NonNull;

import com.eyes3d.eyes3dplayer.utils.PlayerStateApt;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public abstract class AbstractPlayerEngine implements PlayerEngine {
    private static final String TAG = "AbstractPlayerEngine===========>";
    protected PlayerStateApt mApt;
    private Object mObserver;


    abstract protected void initPlayer();

    @Override
    public void addStateObserver(@NonNull Object observer) {
        this.mObserver=observer;
        mApt = new PlayerStateApt(observer);
        mApt.invokeOnStarted();
        initPlayer();
    }
}
