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
import com.eyes3d.eyes3dplayer.utils.EyesLog;
import com.eyes3d.eyes3dplayer.utils.ParamsUtils;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：
 */
public class Eyes2DVideoView extends BaseVideoView implements VedioPlayAndStopView.OnClickListener {

    private static final String TAG = "Eyes2DVideoView";
    public static final int AUTO_DISMISS_TIME_MILLIS = 5000;

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
    /*暂停/播放按钮*/
    private VedioPlayAndStopView mPlayAndStopView;
    /*左边布局栏*/
    private EyesVedioLeftLayout mLeftLayout;
    /*右边布局栏*/
    private EyesVedioRightLayout mRightLayout;

    @Override
    protected int retRootLayout() {
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
        mLeftLayout = findViewById(R.id.vedio_left_layout);
        mRightLayout = findViewById(R.id.vedio_right_layout);

        mPlayAndStopView.setOnClickListener(this);
    }

    /*开始创建播放器*/
    @PlayerState(state = State.ON_CREATE)
    public void onPlayerCreate() {
        Log.e(TAG, "开始创建播放器");
        mBufferingView.show();//这里在Activity onResume之前调用，显示不了
    }

    /*准备完毕·*/
    @PlayerState(state = State.ON_PREPARED)
    public void onPrepared(PlayerController playerCtrl) {
        Log.e(TAG, "准备完毕");
        playerCtrl.start();
        if (mBufferingView.isShowing()){
            mBufferingView.dismiss();
        }
    }


    /*缓冲开始*/
    @PlayerState(state = State.ON_BUFFERING_START)
    public void onBufferingStart(PlayerController playerCtrl) {
        Log.e(TAG, "缓冲开始");
        if (!mBufferingView.isShowing()){
            mBufferingView.show();
        }
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
        mPlayAndStopView.pause();
    }

    /*出现错误*/
    @PlayerState(state = State.ON_ERROR)
    public void onPlayError(PlayerController playerCtrl, int err) {
        mBufferingView.dismiss();
    }

    @Override
    protected PlayerController initPlayer() {
        ParamsUtils.checkNotNull(mLifecycleOwner, "mLifecycleOwner 不允许为 null");
        ParamsUtils.checkNotNull(mPath, "mPath 不允许为 null");
        return EyesPlayer.create2D(mEngine, mLifecycleOwner, this, mSurfaceView, mPath);
    }

    /*单击屏幕*/
    @Override
    public void onSingleTap(MotionEvent e) {
        if (isFloatViewShowing) {
            isFloatViewShowing = false;
            dismissFloatView();
        } else {
            isFloatViewShowing = true;
            showFloatView(mPlayerCtrl.isPlaying());

        }
    }

    /*双击屏幕*/
    @Override
    public void onDoubleTap(MotionEvent e) {
        if (mPlayerCtrl == null) return;
        if (mPlayerCtrl.isPlaying()) {
            mPlayerCtrl.pause();
            mPlayAndStopView.pause();
        } else {
            mPlayerCtrl.start();
            mPlayAndStopView.play();
            mPlayAndStopView.autoDismiss(AUTO_DISMISS_TIME_MILLIS);
        }
    }

    @Override
    public void onHorizontalScroll(MotionEvent e) {
        EyesLog.e(this,"水平方向滑动");
    }

    @Override
    public void onVerticalScroll(MotionEvent e) {
        EyesLog.e(this,"垂直方向滑动");

    }


    private boolean isFloatViewShowing = false;


    protected void showFloatView(boolean autoDismiss) {
        mTitleLayout.show();
        mBottomLayout.show();
        mLeftLayout.show();
        mRightLayout.show();

        if (autoDismiss) {
            postDelayed(this::dismissFloatView, AUTO_DISMISS_TIME_MILLIS);
        }
    }

    protected void dismissFloatView() {
        mTitleLayout.dismiss();
        mBottomLayout.dismiss();
        mLeftLayout.dismiss();
        mRightLayout.dismiss();
    }

    @Override
    public void onPlay() {
        if (mPlayerCtrl != null && !mPlayerCtrl.isPlaying()) {
            mPlayerCtrl.start();
            mPlayAndStopView.autoDismiss(AUTO_DISMISS_TIME_MILLIS);
        }

    }

    @Override
    public void onPause() {
        if (mPlayerCtrl != null && mPlayerCtrl.isPlaying()) {
            mPlayerCtrl.pause();
        }
    }
}
