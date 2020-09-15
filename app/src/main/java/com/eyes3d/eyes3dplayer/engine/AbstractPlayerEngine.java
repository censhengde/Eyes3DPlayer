package com.eyes3d.eyes3dplayer.engine;

import androidx.annotation.NonNull;

import com.eyes3d.eyes3dplayer.utils.PlayerStateApt;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public abstract class AbstractPlayerEngine implements PlayerEngine {
    private static final String TAG = "AbstractPlayerEngine===========>";
    protected  final PlayerStateApt mApt =new PlayerStateApt();



    abstract protected void initPlayer();

    @Override
    public void addStateObserver(@NonNull Object observer) {
        mApt.addObserver(observer);

        // TODO: 2020/9/15 以下两句相对耗时，后续应当剥离此处
        mApt.invokeOnCreate();
        initPlayer();
    }

    @Override
    public void start() {
      mApt.invokeOnStart();
    }

    public void pause(){
        mApt.invokeOnPause();
    }

    @Override
    public void stop() {
        mApt.invokeOnStop();
    }
}
