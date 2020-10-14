package com.eyes3d.eyes3dplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.eyes3d.eyes3dplayer.EyesPlayer2;
import com.eyes3d.eyes3dplayer.engine.PlayerEngine;
import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.impl.OnVideoScreenGestureListener;
import com.eyes3d.eyes3dplayer.listener.OnScreenGestureListener;

import org.jetbrains.annotations.NotNull;

import static com.eyes3d.eyes3dplayer.utils.ParamsChecker.checkNotNull;

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

//    @HidesMembers
//    public EyesPlayer2.Builder getEngineBuider() {
//        return mEngineBuider;
//    }

    protected EyesPlayer2.Builder mEngineBuider;

    private GestureDetector mGestureDetector;
    private OnVideoScreenGestureListener mGestureListener;
    protected PlayerController mPlayerCtrl;


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

    @SuppressLint("ClickableViewAccessibility")
    public BaseVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mGestureListener = new OnVideoScreenGestureListener(this);
        mGestureDetector = new GestureDetector(context, mGestureListener);
        mGestureDetector.setIsLongpressEnabled(false);//禁止长按
        this.setOnTouchListener((v, event) -> {
//            EyesLog.e(this,"setOnTouchListener 事件");
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (mGestureListener.mHasFFREW) {
                    BaseVideoView.this.onScrollUp(event);
                    mGestureListener.mHasFFREW = false;
                }
            }
            return mGestureDetector.onTouchEvent(event);
        });
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



}
