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

    public void setEngine(@Nullable IPlayerEngine engine, boolean is3D) {
        if (engine == null) {
            engine = new SystemPlayerEngine(mContext);
        }
        mPlayerEngine = engine;
        mPlayerEngine.setDataSource(mPath);
        if (is3D) {
            mPlayerEngine.setSurface(mSurface);
        } else {
            mPlayerEngine.setDisplay(mHolder);
        }
    }

    public IPlayerEngine getEngine() {
        return mPlayerEngine;
    }

    /*2D*/
    private EyesPlayer(LifecycleOwner owner, SurfaceView view, String path) {
        this(null, owner, path);
        mHolder = view.getHolder();
    }

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

    /*3D*/
    private EyesPlayer(@NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        this(null, owner, path);
        mGLSurfaceView = view;
        mSurface = renderer.getSurface();
        mPlayerEngine.setSurface(mSurface);
    }

    private EyesPlayer( IPlayerEngine engine,@NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        this(engine, owner, path);
        mGLSurfaceView = view;
        mSurface = renderer.getSurface();
        mPlayerEngine.setSurface(mSurface);
    }

    /*创建2D播放器*/
    public static EyesPlayer create2D(@NonNull LifecycleOwner owner, SurfaceView view, String path) {
        return new EyesPlayer(owner, view, path);
    }

    /*创建3D播放器*/
    public static EyesPlayer create3D(LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        return new EyesPlayer(owner, view, renderer, path);
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


    public static Engine2DBuilder get2DBuilder() {
        return new Engine2DBuilder();
    }


    static class Builder<T extends Builder> {
        IPlayerEngine mEngine;

        public T setEngine(IPlayerEngine engine) {
            this.mEngine = engine;
            return (T) this;
        }

        public T setDataSource(String path) {
            this.mEngine.setDataSource(path);
            return (T) this;
        }

        public EyesPlayer buildEngine() {
            return null;
        }
    }

    public static class Engine3DBuilder extends Builder<Engine3DBuilder> {
        IEyes3DRenderer mRenderer;
        private GLSurfaceView mGLView;


        public Engine3DBuilder setSurface(Surface surface) {
            this.mEngine.setSurface(surface);
            return this;
        }

        public Engine3DBuilder setRenderer(IEyes3DRenderer renderer) {
            this.mRenderer = renderer;
            return this;
        }

        public Engine3DBuilder setGLSurfaceView(GLSurfaceView view) {
            this.mGLView = view;
            return this;
        }

    }

    public static class Engine2DBuilder extends Builder<Engine2DBuilder> {


        public Engine2DBuilder setDisplay(SurfaceHolder holder) {
            super.mEngine.setDisplay(holder);
            return this;
        }
    }
}
