package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.eyes3d.eyes3dplayer.engine.PlayerEngine;
import com.eyes3d.eyes3dplayer.engine.SystemPlayerEngine;
import com.eyes3d.eyes3dplayer.renderer.Eyes3DRenderer;
import com.eyes3d.eyes3dplayer.utils.EyesLog;
import com.eyes3d.eyes3dplayer.utils.ParamsUtils;
import com.eyes3d.eyes3dplayer.utils.PlayerStateApt;

import org.jetbrains.annotations.NotNull;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：旨在改进接口友好性
 */
public final class EyesPlayer2 implements LifecycleObserver {
    private static final String TAG = "EyesPlayer===>";

    public static Eyes2DBuilder create2D() {
        return new Eyes2DBuilder();
    }

    public static Eyes3DBuilder create3D() {
        return new Eyes3DBuilder();
    }

    public static class Builder<T extends Builder> implements LifecycleObserver {

        protected String mPath;
        protected LifecycleOwner mOwner;
        protected Object mStateObserver;
        protected PlayerEngine mEngine;
        protected Context mContext;
//        protected final PlayerStateApt mApt = new PlayerStateApt();


        public T setContext(@NotNull Context context) {
            this.mContext = context;
            return (T) this;
        }

        public T setDataSource(@NotNull String path) {
            mPath = path;
            return (T) this;
        }

        public T setLifecycleOwner(@NotNull LifecycleOwner owner) {
            mOwner = owner;
            mOwner.getLifecycle().addObserver(this);
            return (T) this;
        }

        public T setStateObserver(@NotNull Object stateObserver) {
            mStateObserver = stateObserver;
            return (T) this;
        }

        public void setEngine(@Nullable PlayerEngine engine) {
            mEngine = engine;
        }

        public PlayerController create() {
            return this.mEngine;
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        protected void onResume() {
            Log.e(TAG, "onResume");


        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        protected void onPause() {
            Log.e(TAG, "onPause执行");
            if (mEngine != null) {
                mEngine.pause();
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        protected void onDestroy() {
            EyesLog.e(this, "onDestroy");
            if (mEngine != null) {
                mEngine.reset();
                mEngine.release();
                mEngine = null;
            }
        }
    }

    public static class Eyes2DBuilder extends Builder<Eyes2DBuilder> {
        private SurfaceView mSurfaceView;

        public Eyes2DBuilder setSurfaceView(SurfaceView sf) {
            mSurfaceView = sf;

            return this;
        }

        public PlayerController create() {
            /*检查参数*/
            ParamsUtils.checkNotNull(mStateObserver, "StateObserver 不允许为 null");
            ParamsUtils.checkNotNull(mPath, "DataSource 不允许为 null");
            ParamsUtils.checkNotNull(mSurfaceView, "SurfaceView 不允许为 null");
            if (mEngine == null) {
                EyesLog.e(this, "mEngine =null");
                mEngine = new SystemPlayerEngine(mContext);
            }
            mEngine.onCreate(mStateObserver);
            mEngine.setDataSource(mPath);
            mEngine.setDisplay(mSurfaceView.getHolder());/*这句不能少*/
            mEngine.prepareAsync();
            mSurfaceView.getHolder().addCallback(new SurfaceHolderCallbackImpl(mEngine));
            return super.create();
        }
    }

    public static class Eyes3DBuilder extends Builder<Eyes3DBuilder> {
        private GLSurfaceView mGLSurfaceView;
        private Eyes3DRenderer mRenderer;

        public Eyes3DBuilder setGLSurfaceView(GLSurfaceView GLSurfaceView) {
            mGLSurfaceView = GLSurfaceView;
            return this;
        }

        public Eyes3DBuilder setRenderer(Eyes3DRenderer renderer) {
            mRenderer = renderer;
            return this;
        }


        @Override
        public PlayerController create() {
            /*检查参数*/
            ParamsUtils.checkNotNull(mStateObserver, "StateObserver 不允许为 null");
            ParamsUtils.checkNotNull(mPath, "DataSource 不允许为 null");
            ParamsUtils.checkNotNull(mGLSurfaceView, "GLSurfaceView 不允许为 null");
            ParamsUtils.checkNotNull(mContext, "Context 不允许为 null");
            ParamsUtils.checkNotNull(mRenderer, "Eyes3DRenderer 不允许为 null");
            if (mEngine == null) {
                mEngine = new SystemPlayerEngine(mContext);
            }
            mEngine.onCreate(mStateObserver);
            mEngine.setDataSource(mPath);
            mGLSurfaceView.setRenderer(mRenderer);/*启动GL线程*/
            mEngine.setSurface(mRenderer.getSurface());
            mEngine.prepareAsync();
            return super.create();
        }
    }


}
