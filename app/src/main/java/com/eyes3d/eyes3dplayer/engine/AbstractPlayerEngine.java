package com.eyes3d.eyes3dplayer.engine;

import com.eyes3d.eyes3dplayer.utils.PlayerStateApt;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public abstract class AbstractPlayerEngine implements PlayerEngine {
    private static final String TAG = "AbstractPlayerEngine===========>";
    protected PlayerStateApt mApt;

    public AbstractPlayerEngine(Object observer) {
        mApt = new PlayerStateApt(observer);
        mApt.invokeOnStarted();
        initPlayer();
    }
    abstract protected void initPlayer();
}
