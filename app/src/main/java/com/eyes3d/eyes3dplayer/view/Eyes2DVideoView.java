package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;


import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.State;
import com.eyes3d.eyes3dplayer.listener.OnClickVedioLeftLayoutListener;
import com.eyes3d.eyes3dplayer.utils.EyesAudioManager;
import com.eyes3d.eyes3dplayer.utils.EyesLog;
import com.eyes3d.eyes3dplayer.utils.ParamsUtils;

import static com.eyes3d.eyes3dplayer.State.ON_PAUSE;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：
 */
public class Eyes2DVideoView extends BaseVideoView implements OnClickVedioLeftLayoutListener {

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

        mLeftLayout.setListener(this);
        mAudioManager = new EyesAudioManager(mContext);
        mBottomLayout.setPlayViewWidth(getWidth());
        mBottomLayout.setPlayViewHeight(getHeight());
    }

    private void initData() {

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
        mPlayAndStopView.onPlayerPrepared(playerCtrl);
        mBottomLayout.onPlayerPrepared(playerCtrl);
        mBufferingView.onPlayerPrepared(playerCtrl);
        mTitleLayout.onPlayerPrepared(playerCtrl);
        mLeftLayout.onPlayerPrepared(playerCtrl);
        mRightLayout.onPlayerPrepared(playerCtrl);
    }

    @PlayerState(state = State.ON_START)
    public void onStartPlay() {
        EyesLog.e(this, "开始播放");
        mPlayAndStopView.onStartPlay();
        mBottomLayout.onStartPlay();
        mPlayAndStopView.autoDismiss(AUTO_DISMISS_TIME_MILLIS);
        dismissFloatViewDelayed(AUTO_DISMISS_TIME_MILLIS);

    }

    /*缓冲开始*/
    @PlayerState(state = State.ON_BUFFERING_START)
    public void onBufferingStart(PlayerController playerCtrl) {
        Log.e(TAG, "缓冲开始");
        if (!mBufferingView.isShowing()) {
            mBufferingView.show();
        }
    }

    /*缓冲结束*/
    @PlayerState(state = State.ON_BUFFERING_END)
    public void onBufferingEnd(PlayerController playerCtrl, long currPosition) {
        Log.e(TAG, "缓冲结束: currPosition=" + currPosition);
        mBufferingView.dismiss();
    }

    @PlayerState(state = ON_PAUSE)
    public void onPlayPause() {
        EyesLog.e(this, "播放暂停");
        mPlayAndStopView.onPlayPause();
        mBottomLayout.onPlayPause();
    }

    @PlayerState(state = State.ON_STOP)
    public void onPlayStop() {
        EyesLog.e(this, "播放停止");
    }

    /*播放完成*/
    @PlayerState(state = State.ON_COMPLETION)
    public void onCompletion(PlayerController playerCtrl) {
        Log.e(TAG, "播放完成");
        mPlayAndStopView.onPlayPause();
        mBottomLayout.onPlayPause();
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
        } else {
            mPlayerCtrl.start();
        }
    }

    @Override
    public void onDown(MotionEvent e) {

    }

    @Override
    public void onFF_REWUp(MotionEvent e) {

    }


    private boolean isFloatViewShowing = false;


    protected void showFloatView(boolean autoDismiss) {
        mTitleLayout.show();
        mBottomLayout.show();
        mLeftLayout.show();
        mRightLayout.show();
        if (autoDismiss) {
            dismissFloatViewDelayed(AUTO_DISMISS_TIME_MILLIS);
        }
    }

    protected void dismissFloatViewDelayed(long delayMillis) {
        postDelayed(this::dismissFloatView, delayMillis);
    }

    protected void dismissFloatView() {
        mTitleLayout.dismiss();
        mBottomLayout.dismiss();
        mLeftLayout.dismiss();
        mRightLayout.dismiss();
    }

    @Override
    public void onLock() {
        Toast.makeText(mContext, "锁定屏幕", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnLock() {
        Toast.makeText(mContext, "解锁屏幕", Toast.LENGTH_SHORT).show();
    }


}
