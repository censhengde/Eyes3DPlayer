package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.eyes3d.eyes3dplayer.engine.PlayerEngine;
import com.eyes3d.eyes3dplayer.engine.SystemPlayerEngine;
import com.eyes3d.eyes3dplayer.renderer.Eyes3DRenderer;
import com.eyes3d.eyes3dplayer.utils.EyesLog;

import org.jetbrains.annotations.NotNull;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：
 */
public final class EyesPlayer implements LifecycleObserver {
    private static final String TAG = "EyesPlayer===>";
    private PlayerEngine mPlayerEngine;
    private PlayerController mPlayerController;


    private EyesPlayer() {
    }

    @Nullable
    private GLSurfaceView mGLSurfaceView;

    public PlayerEngine getEngine() {
        return mPlayerEngine;
    }

    /*2D 三参数*/
    private void create2DEngine(LifecycleOwner owner, Object observer,SurfaceView view, String path) {
        create2DEngine(null, owner, observer,view, path);
    }

    /*2D 四参数*/
    private void create2DEngine(PlayerEngine engine, LifecycleOwner owner, Object observer, SurfaceView view, String path) {
        commonInit(engine, owner,observer, path);
        view.getHolder().addCallback(new SurfaceHolderCallbackImpl(mPlayerEngine));

    }

    private void commonInit(@Nullable PlayerEngine engine, LifecycleOwner owner, Object observer, String path) {
        owner.getLifecycle().addObserver(this);
        //engine=null就采用默认engine
        if (engine == null) {
            if (owner instanceof AppCompatActivity) {
                engine = new SystemPlayerEngine((AppCompatActivity) owner);
            } else if (owner instanceof Fragment) {
                Context context = ((Fragment) owner).getContext();
                engine = new SystemPlayerEngine(context);
            } else {
                throw new RuntimeException("LifecycleOwner必须是AppCompatActivity或Fragment");
            }
        }

        mPlayerEngine = engine;
        mPlayerEngine.addStateObserver(observer);
        mPlayerEngine.setDataSource(path);
        mPlayerEngine.prepareAsync();
        mPlayerController = new PlayerControllerImpl(mPlayerEngine);
    }



    /*3D 四参数*/
    private void create3DEngine(@NotNull LifecycleOwner owner, Object observer, GLSurfaceView view, Eyes3DRenderer renderer, String path) {
        this.create3DEngine(null, owner,observer, view, renderer, path);
    }

    /*3D 五参数*/
    private void create3DEngine(PlayerEngine engine, @NotNull LifecycleOwner owner, Object observer, GLSurfaceView view, Eyes3DRenderer renderer, String path) {
        commonInit(engine, owner,observer, path);
        mGLSurfaceView = view;
        view.setRenderer(renderer);/*启动GL线程*/
        mPlayerEngine.setSurface(renderer.getSurface());
    }

    /*创建2D播放器*/
    public static PlayerController create2D(@NotNull LifecycleOwner owner,Object observer, SurfaceView view, String path) {
        return create2D(null, owner,observer, view, path);
    }

    /*创建2D播放器*/
    public static PlayerController create2D(@Nullable PlayerEngine engine, @NonNull LifecycleOwner owner, Object observer, SurfaceView view, String path) {
        EyesPlayer player = new EyesPlayer();
        player.create2DEngine(engine, owner, observer,view, path);
        return player.mPlayerController;
    }

    /*创建3D播放器*/
    public static PlayerController create3D(@NotNull LifecycleOwner owner, Object observer, GLSurfaceView view, Eyes3DRenderer renderer, String path) {
        return create3D(null, owner,observer, view, renderer, path);
    }

    /*创建3D播放器*/
    public static PlayerController create3D(@Nullable PlayerEngine engine, LifecycleOwner owner, Object observer, GLSurfaceView view, Eyes3DRenderer renderer, String path) {
        EyesPlayer player = new EyesPlayer();
        player.create3DEngine(engine, owner,observer, view, renderer, path);
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
        this.pause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        EyesLog.e(this,"onDestroy");
        this.release();
    }

    public void pause() {
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
        mPlayerEngine.pause();
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
        private final PlayerEngine mEngine;

        PlayerControllerImpl(PlayerEngine engine) {
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
        public boolean isPlaying() {
            return mEngine.isPlaying();
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
