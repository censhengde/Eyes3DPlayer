package com.eyes3d.eyes3dplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;

import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.IPlayerEngine;
import com.eyes3d.eyes3dplayer.PlayerController;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：
 */
public class Eyes2DVideoView extends BaseVideoView<SurfaceView> {


    public Eyes2DVideoView(Context context) {
        super(context);
    }

    public Eyes2DVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected SurfaceView setPlayView(Context context) {
        return new SurfaceView(context);
    }


    @Override
    protected PlayerController initPlayer(LifecycleOwner owner, SurfaceView playView, String path) {
        if (mEngine == null) {
            return EyesPlayer.create2D(owner, this, playView, path);
        } else {
            return EyesPlayer.create2D(mEngine, owner, this, playView, path);
        }
    }


}
