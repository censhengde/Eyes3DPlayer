package com.eyes3d.eyes3dplayer;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public abstract class AbstractPlayerEngine implements IPlayerEngine{
    private static final String TAG = "AbstractPlayerEngine===========>";
    protected PlayerStateApt mApt;

    public AbstractPlayerEngine(Object observer) {
        mApt = new PlayerStateApt(observer);
        initPlayer();
    }
    abstract protected void initPlayer();
}
