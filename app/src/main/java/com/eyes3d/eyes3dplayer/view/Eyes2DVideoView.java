package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.State;
import com.eyes3d.eyes3dplayer.utils.EyesAudioManager;
import com.eyes3d.eyes3dplayer.utils.EyesLog;
import com.eyes3d.eyes3dplayer.utils.ParamsUtils;

import static com.eyes3d.eyes3dplayer.State.ON_PAUSE;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：
 */
public class Eyes2DVideoView extends BaseVideoView implements View.OnClickListener {

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
    private VideoPlayAndPauseButton mPlayAndStopView;
    /*进度*/
    private TextView mTvProgressText;
    /*左边布局栏*/
    private EyesVedioLeftLayout mLeftLayout;
    /*右边布局栏*/
    private EyesVedioRightLayout mRightLayout;

    private EyesAudioManager mAudioManager;

    @Override
    protected int retRootLayout() {
        return R.layout.vedio_2d_layout;
    }

    public Eyes2DVideoView(Context context) {
        super(context);
    }

    public Eyes2DVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
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
        mTvProgressText = findViewById(R.id.tv_progress_text);

        mLeftLayout.setOnClickListener(this);
        mAudioManager = new EyesAudioManager(mContext);
        mBottomLayout.setPlayViewWidth(getWidth());
        mBottomLayout.setPlayViewHeight(getHeight());

        mTitleLayout.setOnClickListener(v -> {
            doResetAndShowFloatView();
        });
        mBottomLayout.setOnClickListener(v -> {
            doResetAndShowFloatView();
        });
        mRightLayout.setOnClickListener(v -> {
            doResetAndShowFloatView();
        });
        mLeftLayout.setOnClickListener(v -> {
            doResetAndShowFloatView();
        });
    }

    private void initData() {

    }

    /*开始创建播放器*/
    @PlayerState(value = State.ON_CREATE)
    public void onPlayerCreate() {
        Log.e(TAG, "开始创建播放器");
        mBufferingView.show();//这里在Activity onResume之前调用，显示不了
    }

    /*准备完毕·*/
    @PlayerState(value = State.ON_PREPARED)
    public void onPrepared(PlayerController playerCtrl) {
        Log.e(TAG, "准备完毕");
        playerCtrl.start();
        mPlayAndStopView.onPlayerPrepared(playerCtrl);
        mBottomLayout.onPlayerPrepared(playerCtrl);
        mBufferingView.onPlayerPrepared(playerCtrl);
        mTitleLayout.onPlayerPrepared(playerCtrl);
        mLeftLayout.onPlayerPrepared(playerCtrl);
        mRightLayout.onPlayerPrepared(playerCtrl);
    }

    @PlayerState(value = State.ON_START)
    public void onStartPlay() {
        EyesLog.e(this, "开始播放");
        mPlayAndStopView.onStartPlay();
        mBottomLayout.onStartPlay();
        mPlayAndStopView.autoDismiss(AUTO_DISMISS_TIME_MILLIS);
        doResetAndShowFloatView();
    }

    /*缓冲开始*/
    @PlayerState(value = State.ON_BUFFERING_START)
    public void onBufferingStart(PlayerController playerCtrl) {
        Log.e(TAG, "缓冲开始");
        if (!mBufferingView.isShowing()) {
            mBufferingView.show();
        }
    }

    /*缓冲结束*/
    @PlayerState(value = State.ON_BUFFERING_END)
    public void onBufferingEnd(PlayerController playerCtrl, long currPosition) {
        Log.e(TAG, "缓冲结束: currPosition=" + currPosition);
        mBufferingView.dismiss();
    }

    @PlayerState(ON_PAUSE)
    public void onPlayPause() {
        EyesLog.e(this, "播放暂停");
        mPlayAndStopView.onPlayPause();
        mBottomLayout.onPlayPause();
    }

    @PlayerState(State.ON_STOP)
    public void onPlayStop() {
        EyesLog.e(this, "播放停止");
    }

    /*播放完成*/
    @PlayerState(State.ON_COMPLETION)
    public void onCompletion(PlayerController playerCtrl) {
        Log.e(TAG, "播放完成");
        mPlayAndStopView.onPlayPause();
        mBottomLayout.onPlayPause();
    }

    /*出现错误*/
    @PlayerState(State.ON_ERROR)
    public void onPlayError(PlayerController playerCtrl, int err) {
        mBufferingView.dismiss();
    }

    @Override
    protected PlayerController initPlayer() {
        ParamsUtils.checkNotNull(mLifecycleOwner, "mLifecycleOwner 不允许为 null");
        ParamsUtils.checkNotNull(mPath, "mPath 不允许为 null");
        return EyesPlayer.create2D(mEngine, mLifecycleOwner, this, mSurfaceView, mPath);
    }

    @Override
    public void onBrightnessGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        EyesLog.e(this, "亮度调节");
    }

    @Override
    public void onVolumeGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        EyesLog.e(this, "音量调节");
    }

    @Override
    public void onHorizontalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        EyesLog.e(this, "onHorizontalScroll ");
        mBottomLayout.onHorizontalScroll(e1, e2, distanceX, distanceY);
    }

    /*单击屏幕*/
    @Override
    public void onSingleTapConfirmed(MotionEvent e) {
        EyesLog.e(this, "单击");
        if (mIsFloatViewShowing) {
            dismissFloatViewNow();
        } else {
            if (mPlayerCtrl == null) return;
            doResetAndShowFloatView();
        }
    }

    /*双击屏幕*/
    @Override
    public void onDoubleTap(MotionEvent e) {
        if (mPlayerCtrl == null) return;
        if (mPlayerCtrl.isPlaying()) {
            mPlayerCtrl.pause();
        } else {
            mPlayerCtrl.start();
        }
    }

    @Override
    public void onDown(MotionEvent e) {
        EyesLog.e(this, "onDown===>");
        //
//        if (mIsFloatViewShowing) {
//
//            removeCallbacks(mDismissTask);
//            postDelayed(mDismissTask,AUTO_DISMISS_TIME_MILLIS);
//        }
    }

    @Override
    public void onFF_REWUp(MotionEvent e) {

    }


    private boolean mIsFloatViewShowing = false;


    /*可频繁重复调用多次*/
    private void doResetAndShowFloatView() {
        //每次调用意味着重置自动隐藏时间
        removeCallbacks(mDismissTask);
        mTitleLayout.show();
        mBottomLayout.show();
        mLeftLayout.show();
        mRightLayout.show();
        mIsFloatViewShowing = true;
        /*因为各种条件导致无法及时隐藏*/
        dismissFloatViewDelayed();

    }


    private final Runnable mDismissTask = this::dismissFloatViewNow;


    /*立即隐藏*/
    private void dismissFloatViewNow() {
        mIsFloatViewShowing = false;
        if (mTitleLayout != null) {
            mTitleLayout.dismiss();
        }
        if (mBottomLayout != null) mBottomLayout.dismiss();
        if (mLeftLayout != null) mLeftLayout.dismiss();
        if (mRightLayout != null) mRightLayout.dismiss();
    }

    /*无操作5s后自动隐藏*/
    private void dismissFloatViewDelayed() {
        /*因为各种条件导致无法及时隐藏*/
        if (mPlayerCtrl == null || !mPlayerCtrl.isPlaying()) return;
        if (isAllFloatViewNoTouch()) {
            postDelayed(mDismissTask, AUTO_DISMISS_TIME_MILLIS);
        }
    }

    private boolean isAllFloatViewNoTouch() {
        return !mTitleLayout.isOnTouching() && !mBottomLayout.isOnTouching()
                && !mLeftLayout.isOnTouching() && !mRightLayout.isOnTouching();
    }

//    @Override
//    public void onLock() {
//        Toast.makeText(mContext, "锁定屏幕", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onUnLock() {
//        Toast.makeText(mContext, "解锁屏幕", Toast.LENGTH_SHORT).show();
//    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.layout.vedio_left_layout: {

                break;
            }

            case R.layout.vedio_right_layout: {

                break;
            }

            case R.layout.vedio_title_layout: {
                break;
            }

            case R.layout.vedio_bottom_layout: {
                doResetAndShowFloatView();
                break;

            }
        }
    }
}
