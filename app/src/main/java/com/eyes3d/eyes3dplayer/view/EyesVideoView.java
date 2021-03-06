package com.eyes3d.eyes3dplayer.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.State;
import com.eyes3d.eyes3dplayer.utils.EyesAudioManager;
import com.eyes3d.eyes3dplayer.utils.EyesLog;
import com.eyes3d.eyes3dplayer.utils.ParamsChecker;

import static com.eyes3d.eyes3dplayer.State.ON_BUFFERING_END;
import static com.eyes3d.eyes3dplayer.State.ON_BUFFERING_START;
import static com.eyes3d.eyes3dplayer.State.ON_PAUSE;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：
 */
public class EyesVideoView extends BaseVideoView<EyesVideoView > {

    private static final String TAG = "Eyes2DVideoView";
    public static final int AUTO_DISMISS_TIME_MILLIS = 5000;

    private SurfaceView mSurfaceView;
    /*音量条*/
    protected EyesVolumeBar mVolumeBar;
    /*屏幕亮度条*/
    private EyesBringhtnessBar mBringhtnessBar;
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

    public EyesVideoView(Context context) {
        super(context);
    }

    public EyesVideoView(Context context, AttributeSet attrs) {
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
        mBringhtnessBar = findViewById(R.id.bringhtness_bar);
        mVolumeBar = findViewById(R.id.volume_bar);

        mAudioManager = new EyesAudioManager(mContext);
//        mBottomLayout.setPlayViewWidth(getWidth());
//        mBottomLayout.setPlayViewHeight(getHeight());

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



    public EyesVideoView setCurrentActivity(@NonNull Activity activity) {
        mBringhtnessBar.setCurrentActivity(activity);
        return this;
    }

    /*开始创建播放器*/
    @PlayerState(State.ON_CREATE)
    public void onPlayerCreate() {
        Log.e(TAG, "开始创建播放器");
        mBufferingView.show();//这里在Activity onResume之前调用，显示不了
    }

    /*准备完毕·*/
    @PlayerState(State.ON_PREPARED)
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

    @PlayerState(State.ON_START)
    public void onStartPlay() {
        EyesLog.e(this, "开始播放");
        mPlayAndStopView.onStartPlay();
        mBottomLayout.onStartPlay();
        mPlayAndStopView.autoDismiss(AUTO_DISMISS_TIME_MILLIS);
        doResetAndShowFloatView();
    }

    /*缓冲开始*/
    @PlayerState(ON_BUFFERING_START)
    public void onBufferingStart(PlayerController playerCtrl) {
        Log.e(TAG, "缓冲开始");
        if (!mBufferingView.isShowing()) {
            mBufferingView.show();
        }
    }

    /*缓冲结束*/
    @PlayerState(ON_BUFFERING_END)
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
        ParamsChecker.checkNotNull(mLifecycleOwner, "mLifecycleOwner 不允许为 null");
        ParamsChecker.checkNotNull(mPath, "mPath 不允许为 null");
        return EyesPlayer.create2D(mEngine, mLifecycleOwner, this, mSurfaceView, mPath);
    }

    /*亮度手势*/
    @Override
    public void onBrightnessGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        EyesLog.e(this, "亮度调节");
        if (mBringhtnessBar == null) return;
        if (mBringhtnessBar.getVisibility() == GONE) {
            mBringhtnessBar.setVisibility(VISIBLE);
        }
        mBringhtnessBar.onAdjustGesture(e1, e2, super.getMeasuredHeight(), distanceY);
    }

    /*音量手势*/
    @Override
    public void onVolumeGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        EyesLog.e(this, "音量调节");
        if (mVolumeBar == null) return;
        if (mVolumeBar.getVisibility() == GONE) {
            mVolumeBar.setVisibility(VISIBLE);
        }
        mVolumeBar.onAdjustGesture(e1, e2, super.getMeasuredHeight(), distanceY);
    }

    /*快进、快退手势*/
    @Override
    public void onHorizontalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        EyesLog.e(this, "onHorizontalScroll ");
        mBottomLayout.onHorizontalScroll(e1, e2, mTvProgressText, null, distanceX);
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
        mBottomLayout.onDown(e);
    }

    @Override
    public void onScrollUp(MotionEvent e) {
        mBottomLayout.onScrollUp();
        /*隐藏亮度条*/
        if (mBringhtnessBar != null && mBringhtnessBar.getVisibility() == VISIBLE) {
            mBringhtnessBar.setVisibility(GONE);
        }
        /*隐藏音量条*/
        if (mVolumeBar != null && mVolumeBar.getVisibility() == VISIBLE) {
            mVolumeBar.setVisibility(GONE);
        }
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


}
