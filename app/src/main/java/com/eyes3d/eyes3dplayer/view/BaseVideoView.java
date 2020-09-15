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
import com.eyes3d.eyes3dplayer.listener.OnScreenGestureListener;

import org.jetbrains.annotations.NotNull;

import static com.eyes3d.eyes3dplayer.utils.ParamsUtils.checkNotNull;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public abstract class BaseVideoView extends RelativeLayout implements OnScreenGestureListener {
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

    protected abstract @LayoutRes
    int retRootLayout();

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
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private static class SimpleOnGestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
        private static final float MIN_FLING_DISTANCE = 0f;
        private OnScreenGestureListener mScreenGestureListener;
        private boolean mOneTimeScroll = false;

        public SimpleOnGestureListenerImpl(OnScreenGestureListener listener) {
            super();
            mScreenGestureListener = listener;
        }

        //双击时也触发
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("=====>", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);

        }

        /*
         * distanceX：上次滑动(调用onScroll)到这次滑动的X轴的距离px，不是e1点到e2点的X轴的距离
         * */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
           //这里的action只有move
            switch (e2.getAction()) {
                case MotionEvent.ACTION_MOVE: //横向滑动
                    if (Math.abs(e2.getX() - e1.getX()) >= Math.abs(e2.getY() - e1.getY())) {
                        mScreenGestureListener.onHorizontalScroll(e2);
                    }
                    //纵向滑动
                    else {
                        mScreenGestureListener.onVerticalScroll(e2);
                    }
                    break;
                case MotionEvent.ACTION_UP:mScreenGestureListener.onHorizontalScrollUp(e2);
                    break;
            }

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("====>", "onShowPress");
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("====>", "onDown");
            mOneTimeScroll = true;
            return true;
        }



        /*双击屏幕 */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("====>", "onDoubleTap");
            mScreenGestureListener.onDoubleTap(e);
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e("====>", "onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("====>", "onSingleTapConfirmed");
            mScreenGestureListener.onSingleTapConfirmed(e);
            return true;
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.e("====>", "onContextClick");
            return true;
        }
    }


}
