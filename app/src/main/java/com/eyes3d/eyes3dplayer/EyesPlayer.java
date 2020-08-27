package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：
 */
public final class EyesPlayer implements LifecycleObserver {

    @Nullable
    private SurfaceHolder mHolder;
    @NotNull
    private IPlayerEngine mPlayerEngine;
    private Context mContext;
    private String mPath;
    private Surface mSurface;
    @Nullable
    private GLSurfaceView mGLSurfaceView;

    public IPlayerEngine getEngine() {
        return mPlayerEngine;
    }

    /*2D 三参数*/
    private EyesPlayer(LifecycleOwner owner, SurfaceView view, String path) {
        this(null, owner, view, path);
    }

    /*2D 四参数*/
    private EyesPlayer(IPlayerEngine engine, LifecycleOwner owner, SurfaceView view, String path) {
        this(engine, owner, path);
        mHolder = view.getHolder();
    }

    /*公用构造器*/
    private EyesPlayer(@Nullable IPlayerEngine engine, LifecycleOwner owner, String path) {
        owner.getLifecycle().addObserver(this);
        if (owner instanceof AppCompatActivity) {
            mContext = ((AppCompatActivity) owner).getApplicationContext();
        } else if (owner instanceof Fragment) {
            mContext = ((Fragment) owner).getContext().getApplicationContext();
        } else {
            throw new RuntimeException("LifecycleOwner 必须是Activity/Fragment");
        }
        mPath = path;
        //engine=null就采用默认engine
        if (engine == null) {
            engine = new SystemPlayerEngine(mContext);
        }
        mPlayerEngine = engine;
        mPlayerEngine.setDataSource(mPath);
    }

    /*3D 四参数*/
    private EyesPlayer(@NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        this(null, owner, view, renderer, path);
    }

    /*3D 五参数*/
    private EyesPlayer(IPlayerEngine engine, @NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        this(engine, owner, path);
        mGLSurfaceView = view;
        mGLSurfaceView.setRenderer(renderer);/*启动GL线程*/
        mSurface = renderer.getSurface();
        mPlayerEngine.setSurface(mSurface);
    }

    /*创建2D播放器*/
    public static EyesPlayer create2D(@NotNull LifecycleOwner owner, SurfaceView view, String path) {
        return new EyesPlayer(owner, view, path);
    }

    /*创建2D播放器*/
    public static EyesPlayer create2D(@Nullable IPlayerEngine engine, @NonNull LifecycleOwner owner, SurfaceView view, String path) {
        return new EyesPlayer(engine, owner, view, path);
    }

    /*创建3D播放器*/
    public static EyesPlayer create3D(@NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        return new EyesPlayer(owner, view, renderer, path);
    }

    /*创建3D播放器*/
    public static EyesPlayer create3D(@Nullable IPlayerEngine engine, LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        return new EyesPlayer(engine, owner, view, renderer, path);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onResume();
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause() {
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
        mPlayerEngine.pause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        mPlayerEngine.release();
    }


}
