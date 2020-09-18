package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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

    private float mDownX;
    private float mDownY;
    private boolean mHorizontalScrolled = false;
    private boolean mVerticalScrolled = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownX = event.getX();
//                mDownY = event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float distX = Math.abs(event.getX() - mDownX);
//                float distY = Math.abs(event.getY() - mDownY);
//                /*水平滑动*/
//                if (distX >= distY || mHorizontalScrolled) {
//                    mHorizontalScrolled = true;
//                    this.onHorizontalScroll(event);
//                    return true;
//                } else if (distX < distY || mVerticalScrolled) {/*垂直滑动*/
//                    mVerticalScrolled = true;
//                    this.onVerticalScroll(event);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                mHorizontalScrolled = false;
//                mVerticalScrolled = false;
//                break;
//        }
        return mGestureDetector.onTouchEvent(event);
    }

    private static class SimpleOnGestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
        private static final float MIN_FLING_DISTANCE = 0f;
        private OnScreenGestureListener mScreenGestureListener;
        private boolean mOneTimeScroll = false;
        private final Handler mHandler;
        private int mMsgs = 0;
        private boolean mHorizontalScrolled = false;
        private boolean mVerticalScrolled = false;
        public SimpleOnGestureListenerImpl(OnScreenGestureListener listener) {
            super();
            mScreenGestureListener = listener;
            mHandler = new Handler(Looper.getMainLooper());
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
            if (Math.abs(e2.getX() - e1.getX()) >= Math.abs(e2.getY() - e1.getY())) {
                mHorizontalScrolled=true;
                mMsgs++;
                mHandler.postDelayed(() -> {
                    if (mMsgs == 1) {
                        if (mHorizontalScrolled&&!mVerticalScrolled){
                            mScreenGestureListener.onHorizontalScrollUp(e2);
                        }
                    }
                    mMsgs--;
                }, 500);
                mScreenGestureListener.onHorizontalScroll(e2);
            }
            //纵向滑动
            else {
                mMsgs++;
                mVerticalScrolled=true;
                mHandler.postDelayed(() -> {
                    if (mMsgs == 1) {
                        mScreenGestureListener.onVerticalScrollUp(e2);
                    }
                    mMsgs--;
                }, 500);
                mScreenGestureListener.onVerticalScroll(e2);
            }

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if (e2.getAction()==MotionEvent.ACTION_UP)

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
