package com.eyes3d.eyes3dplayer;

import android.util.Log;
import android.view.SurfaceHolder;

import com.eyes3d.eyes3dplayer.engine.PlayerEngine;

/**
 * Shengde·Cen on 2020/8/31
 * 说明：
 */
final class SurfaceHolderCallbackImpl implements SurfaceHolder.Callback {
    private static final String TAG = "SurfaceHolderCallback";
    private final PlayerEngine mEngine;

    SurfaceHolderCallbackImpl(PlayerEngine engine) {
        mEngine = engine;
    }


    /*在onResume()之后调用*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated ");
        mEngine.setDisplay(holder);
        mEngine.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged ");
    }

    /*在onPause()之后调用*/
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mEngine.pause();
        Log.e(TAG, "surfaceDestroyed ");
    }
}
