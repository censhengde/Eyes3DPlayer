package com.eyes3d.eyes3dplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.lifecycle.LifecycleOwner;

import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.IPlayerEngine;
import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.State;

import kotlin.internal.HidesMembers;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
 abstract class BaseVideoView<V extends View> extends RelativeLayout {
    private static final String TAG = "VideoView===========>";
    protected V mPlayView;
    protected Context mContext;
    protected IPlayerEngine mEngine;
    protected String mPath;

    public void setEngine(IPlayerEngine engine) {
        mEngine = engine;
    }

    /*音量条*/
    private EyesVolumeBar mVolumeBar;
    /*屏幕亮度条*/
    private EyesScreenBringhtnessBar mBringhtnessBar;
    /*底部操作栏*/
    private EyesVideoBottomLayout mBottomLayout;
    /*顶部操作栏*/
    private EyesVideoTopLayout mTopLayout;
    /*缓冲进度条*/
    private BufferingProgressBar mBufferingProgressBar;

    private GestureDetector mGestureDetector;
    private PlayerController mPlayerController;

    public PlayerController getPlayerController() {
        checkNotNull(mPlayerController, "PlayerController为null,请先调用setDataSource");
        return mPlayerController;
    }

    protected LifecycleOwner mLifecycleOwner;

    public BaseVideoView(Context context) {
        this(context, null);
    }

    public BaseVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mGestureDetector = new GestureDetector(context, new SimpleOnGestureListenerImpl(this));
        initSurfaceView(context);

    }

    private void initSurfaceView(Context context) {
        mPlayView = setPlayView(context);
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        mPlayView.setLayoutParams(lp);
        this.addView(mPlayView);
    }

    protected abstract V setPlayView(Context context);

    public void addLifecycleOwner(LifecycleOwner owner) {
        checkNotNull(owner, "LifecycleOwner不允许为null");
        this.mLifecycleOwner = owner;
    }

    protected static void checkNotNull(Object o, String throwMsg) {
        if (o == null) {
            throw new RuntimeException(throwMsg);
        }
    }

    public void setDataSource(String path) {
        checkNotNull(mLifecycleOwner, "请在调用setDataSource之前先调用addLifecycleOwner");
        mPlayerController = initPlayer(mLifecycleOwner, mPlayView, path);
    }

    @HidesMembers
    public void createPlayerEngine(IPlayerEngine engine, LifecycleOwner owner, String path) {
        this.mEngine = engine;
        this.mLifecycleOwner = owner;
        this.mPath = path;
        this.mPlayerController = initPlayer(mLifecycleOwner, mPlayView,mPath);
    }

    protected abstract PlayerController initPlayer(LifecycleOwner owner, V playView, String path);

    /*准备完毕·*/
    @PlayerState(state = State.ON_PREPARED)
    public void onPrepared(PlayerController playerCtrl) {
        Log.e(TAG, "播放准备完毕");
        playerCtrl.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private static class SimpleOnGestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
        private BaseVideoView mVideoView;

        public SimpleOnGestureListenerImpl(BaseVideoView baseVideoView) {
            super();
            mVideoView = baseVideoView;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("=====>", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceX >= distanceY) {
                Log.e("====>", "横向滑动");
            } else {
                Log.e("====>", "纵向滑动");

            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.e("====>", "onFling");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("====>", "onShowPress");
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("====>", "onDown");
            return true;
        }

        private boolean isPlaying = true;

        /*双击屏幕 */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("====>", "onDoubleTap");
            if (mVideoView.getPlayerController().isPlaying()) {
                mVideoView.getPlayerController().pause();
            } else {
                mVideoView.getPlayerController().start();
            }
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e("====>", "onDoubleTapEvent");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("====>", "onSingleTapConfirmed");
            return true;
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.e("====>", "onContextClick");
            return true;
        }
    }
}
