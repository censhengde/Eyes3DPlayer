package com.eyes3d.eyes3dplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Play2DActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "Play2DActivity";
    private String mPath = "http://eyes3d-v2.oss.eyes3d.com.cn/236560018386452487/video/20200820171317-05386294.mp4";
    private String mPath2 = "http://eyes3d-v2.oss.eyes3d.com.cn/201358856309964806/video/20200401141608-03071678.mp4";
    private EyesPlayer mDPlayer;
    private IPlayerEngine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2d);
        SurfaceView sf = findViewById(R.id.sf);
        sf.getHolder().addCallback(this);
        mDPlayer = EyesPlayer.create2D(this, sf, mPath);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDPlayer.getEngine().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /*准备完毕·*/
    @PlayerState(state = State.ON_PREPARED)
    protected void onPrepared() {
        Log.e(TAG, "播放准备完毕");
        mDPlayer.getEngine().start();
    }

    /*缓冲开始*/
    @PlayerState(state = State.ON_BUFFERING_START)
    protected void onBufferingStart() {
        Log.e(TAG, "缓冲开始");
    }

    /*缓冲结束*/
    @PlayerState(state = State.ON_BUFFERING_END)
    protected void onBufferingEnd() {
        Log.e(TAG, "缓冲结束");

    }

    /*播放完成*/
    @PlayerState(state = State.ON_COMPLETION)
    protected void onCompletion() {
        Log.e(TAG, "播放完成");

    }

    /*尺寸改变*/
    @PlayerState(state = State.ON_VIDEO_SIZE_CHANGED)
    protected void onVideoSizeChanged(int width, int height) {
        Log.e(TAG, "尺寸改变"+width+"  "+height);

    }
}
