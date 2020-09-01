package com.eyes3d.eyes3dplayer;

import android.opengl.GLSurfaceView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private static final String TAG = "EyesPlayer===>";
    private IPlayerEngine mPlayerEngine;
    private PlayerController mPlayerController;
    /*采用缓存池减少对IPlayerEngine的创建*/
    private final ArrayMap<Object, IPlayerEngine> mPlayerEngineMap = new ArrayMap<>();


    private EyesPlayer() {
    }

    @Nullable
    private GLSurfaceView mGLSurfaceView;

    public IPlayerEngine getEngine() {
        return mPlayerEngine;
    }

    /*2D 三参数*/
    private void create2DEngine(LifecycleOwner owner, SurfaceView view, String path) {
        create2DEngine(null, owner, view, path);
    }

    /*2D 四参数*/
    private void create2DEngine(IPlayerEngine engine, LifecycleOwner owner, SurfaceView view, String path) {
        commonInit(engine, owner, path);
        view.getHolder().addCallback(new SurfaceHolderCallbackImpl(mPlayerEngine));

    }

    private void commonInit(@Nullable IPlayerEngine engine, LifecycleOwner owner, String path) {
        owner.getLifecycle().addObserver(this);
        //engine=null就采用默认engine
        if (engine == null) {
            engine = new SystemPlayerEngine(owner);
        }
        mPlayerEngine = engine;
        mPlayerEngine.setDataSource(path);
        mPlayerEngine.prepareAsync();
        mPlayerController = new PlayerControllerImpl(mPlayerEngine);
    }

    /*3D 四参数*/
    private void create3DEngine(@NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        this.create3DEngine(null, owner, view, renderer, path);
    }

    /*3D 五参数*/
    private void create3DEngine(IPlayerEngine engine, @NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        commonInit(engine, owner, path);
        mGLSurfaceView = view;
        view.setRenderer(renderer);/*启动GL线程*/
        mPlayerEngine.setSurface(renderer.getSurface());
    }

    /*创建2D播放器*/
    public static PlayerController create2D(@NotNull LifecycleOwner owner, SurfaceView view, String path) {
       return create2D(null,owner,view,path);
    }

    /*创建2D播放器*/
    public static PlayerController create2D(@Nullable IPlayerEngine engine, @NonNull LifecycleOwner owner, SurfaceView view, String path) {
        EyesPlayer player = new EyesPlayer();
        player.create2DEngine(engine,owner, view, path);
        return player.mPlayerController;
    }

    /*创建3D播放器*/
    public static PlayerController create3D(@NotNull LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        return create3D(null,owner,view,renderer,path);
    }

    /*创建3D播放器*/
    public static PlayerController create3D(@Nullable IPlayerEngine engine, LifecycleOwner owner, GLSurfaceView view, IEyes3DRenderer renderer, String path) {
        EyesPlayer player = new EyesPlayer();
        player.create3DEngine(engine,owner, view,renderer, path);
        return player.mPlayerController;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        Log.e(TAG, "onResume");
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onResume();
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause() {
        Log.e(TAG, "onPause执行");
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
        mPlayerEngine.pause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        this.release();
    }

    public void release() {
        if (mPlayerEngine != null) {
            mPlayerEngine.reset();
            mPlayerEngine.release();
            mPlayerEngine = null;
        }
        mGLSurfaceView = null;
        mPlayerController = null;
    }

    private static class PlayerControllerImpl implements PlayerController {
        private final IPlayerEngine mEngine;

        PlayerControllerImpl(IPlayerEngine engine) {
            this.mEngine = engine;
        }

        @Override
        public void start() {
            mEngine.start();
        }

        @Override
        public void pause() {
            mEngine.pause();
        }

        @Override
        public void stop() {
            mEngine.stop();
        }

        @Override
        public void reset() {
            mEngine.reset();
        }

        @Override
        public long getCurrentPosition() {
            return mEngine.getCurrentPosition();
        }

        @Override
        public long getDuration() {
            return mEngine.getDuration();
        }

        @Override
        public void seekTo(int msec) {
            mEngine.seekTo(msec);
        }
    }

}
