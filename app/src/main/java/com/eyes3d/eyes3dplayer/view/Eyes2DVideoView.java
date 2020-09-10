package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;


import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.State;
import com.eyes3d.eyes3dplayer.utils.ParamsUtils;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：
 */
public class Eyes2DVideoView extends BaseVideoView {

    private static final String TAG = "Eyes2DVideoView";
    private SurfaceView mSurfaceView;
    /*音量条*/
    protected EyesVolumeBar mVolumeBar;
    /*屏幕亮度条*/
    private EyesScreenBringhtnessBar mBringhtnessBar;
    /*底部操作栏*/
    private EyesVideoBottomLayout mBottomLayout;
    /*顶部操作栏*/
    private EyesVideoTitleLayout mTitleLayout;
    /*缓冲进度条*/
    private BufferingProgressBar mBufferingView;
    private VedioPlayAndStopView mPlayAndStopView;

    @Override
    public int setContentView() {
        return R.layout.vedio_2d_layout;
    }

    public Eyes2DVideoView(Context context) {
        super(context);
    }

    public Eyes2DVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        mSurfaceView = findViewById(R.id.sfv_vedio_2d);
        mBufferingView = findViewById(R.id.vedio_buffering_view);
        mPlayAndStopView = findViewById(R.id.vedio_play_stop_view);
        mTitleLayout = findViewById(R.id.vedio_title_layout);
        mBottomLayout = findViewById(R.id.vedio_bottom_layout);
    }

    /*开始创建播放器*/
    @PlayerState(state = State.ON_STARTED)
    public void onPlayerStartCreate() {
        mBufferingView.show();
    }

    /*准备完毕·*/
    @PlayerState(state = State.ON_PREPARED)
    public void onPrepared(PlayerController playerCtrl) {
        Log.e(TAG, "播放准备完毕");
        playerCtrl.start();
        mPlayAndStopView.stop();
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
        mBufferingView.dismiss();

    }


    /*播放完成*/
    @PlayerState(state = State.ON_COMPLETION)
    public void onCompletion(PlayerController playerCtrl) {
        Log.e(TAG, "播放完成");

    }

    /*出现错误*/
    @PlayerState(state = State.ON_ERROR)
    public void onPlayError(PlayerController playerCtrl, int err) {
        mBufferingView.dismiss();
    }

    @Override
    protected PlayerController initPlayer() {
        ParamsUtils.checkNotNull(mLifecycleOwner, "mLifecycleOwner 为 null");
        ParamsUtils.checkNotNull(mPath, "mPath 为 null");
            return EyesPlayer.create2D(mEngine, mLifecycleOwner, this, mSurfaceView, mPath);
    }

    /*双击屏幕*/
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (mPlayerController==null) return false;
        if (mPlayerController.isPlaying()) {
            mPlayerController.pause();
        } else {
            mPlayerController.start();
        }
        mPlayAndStopView.onDoubleTap();
        return true;
    }

    /*单击屏幕*/
    @Override
    protected boolean onSingleTapConfirmed(MotionEvent e) {
        mPlayAndStopView.show(mPlayerController.isPlaying());
        return super.onSingleTapConfirmed(e);
    }
}
