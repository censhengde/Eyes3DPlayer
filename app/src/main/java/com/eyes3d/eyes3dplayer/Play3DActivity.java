package com.eyes3d.eyes3dplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Play3DActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity===>";
    private EyesPlayer mDPlayer;

    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Eyes3DSurfaceView glsfv = findViewById(R.id.glsfv);
        mDPlayer = EyesPlayer.create3D(this, glsfv,
                new Eyes3DRenderer(glsfv, R.raw.gl_3d_render_vert_shader, R.raw.gl_3d_render_frag_shader),
                path);
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
