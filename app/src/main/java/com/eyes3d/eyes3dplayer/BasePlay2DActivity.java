package com.eyes3d.eyes3dplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BasePlay2DActivity extends AppCompatActivity {

    private static final String TAG = "Play2DActivity";
    private String mPath = "http://eyes3d-v2.oss.eyes3d.com.cn/236560018386452487/video/20200820171317-05386294.mp4";
    private String mPath2 = "http://eyes3d-v2.oss.eyes3d.com.cn/201358856309964806/video/20200401141608-03071678.mp4";
    protected PlayerController mPlayerCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    protected void initLayout() {
        setContentView(R.layout.activity_play2d);
        SurfaceView sf = findViewById(R.id.sf);
        mPlayerCtrl = EyesPlayer.create2D(new IjkPlayerEngine(this), this, sf, mPath2);/*框架内部已实现LifeCycleObserver，自动处理生命周期相关事宜*/

    }

    /*准备完毕·*/
    @PlayerState(state = State.ON_PREPARED)
    public void onPrepared(PlayerController playerCtrl) {
        Log.e(TAG, "播放准备完毕");
        playerCtrl.start();
    }

    /*缓冲开始*/
    @PlayerState(state = State.ON_BUFFERING_START)
    public void onBufferingStart(PlayerController playerCtrl) {
        Log.e(TAG, "缓冲开始");
    }

    /*缓冲结束*/
    @PlayerState(state = State.ON_BUFFERING_END)
    public void onBufferingEnd(PlayerController playerCtrl, long currPosition) {
        Log.e(TAG, "缓冲结束: currPosition=" + currPosition);

    }

    /*播放完成*/
    @PlayerState(state = State.ON_COMPLETION)
    public void onCompletion(PlayerController playerCtrl) {
        Log.e(TAG, "播放完成");

    }

    /*尺寸改变*/
    @PlayerState(state = State.ON_VIDEO_SIZE_CHANGED)
    public void onVideoSizeChanged(int width, int height) {
        Log.e(TAG, "尺寸改变" + width + "  " + height);

    }

    /*出现错误*/
    @PlayerState(state = State.ON_ERROR)
    public void onPlayError(PlayerController playerCtrl, int err) {

    }
}
