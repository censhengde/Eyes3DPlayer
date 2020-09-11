package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.eyes3d.eyes3dplayer.engine.PlayerEngine;
import com.eyes3d.eyes3dplayer.PlayerController;

import org.jetbrains.annotations.NotNull;

import static com.eyes3d.eyes3dplayer.utils.ParamsUtils.checkNotNull;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public abstract class BaseVideoView extends RelativeLayout {
    private static final String TAG = "VideoView===========>";
    protected Context mContext;
    @Nullable
    protected PlayerEngine mEngine;
    @NonNull
    protected String mPath;


    private GestureDetector mGestureDetector;
    protected PlayerController mPlayerCtrl;

    public PlayerController getPlayerCtrl() {
        checkNotNull(mPlayerCtrl, "PlayerController为null,请先调用setDataSource");
        return mPlayerCtrl;
    }

    protected abstract @LayoutRes int retRootLayout();

    @NotNull
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
        View.inflate(context, retRootLayout(), this);
        initView();
    }

    protected abstract void initView();


    @SuppressWarnings("unchecked cast")
    public <T extends BaseVideoView> T addLifecycleOwner(@NonNull LifecycleOwner owner) {
        checkNotNull(owner, "LifecycleOwner不允许为null");
        this.mLifecycleOwner = owner;
        return (T) this;
    }

    @SuppressWarnings("unchecked cast")
    public <T extends BaseVideoView> T setPlayerEngine(@NonNull PlayerEngine engine) {
        mEngine = engine;
        return (T) this;
    }

    @SuppressWarnings("unchecked cast")
    public <T extends BaseVideoView> T setDataSource(@NotNull String path) {
        checkNotNull(path, "path 不允许为 null");
        mPath = path;
        return (T) this;
    }

    public void createPlayer() {
        mPlayerCtrl = initPlayer();
    }

    protected abstract PlayerController initPlayer();

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN: return true;
//        }
        return true;
    }

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
            if (mVideoView != null) {
                return mVideoView.onDoubleTap(e);
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e("====>", "onDoubleTapEvent");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("====>", "onSingleTapConfirmed");
            return mVideoView.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.e("====>", "onContextClick");
            return true;
        }
    }

    protected boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }
}
