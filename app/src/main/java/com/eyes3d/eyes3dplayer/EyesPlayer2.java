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

import com.eyes3d.eyes3dplayer.engine.PlayerEngine;
import com.eyes3d.eyes3dplayer.renderer.Eyes3DRenderer;

import org.jetbrains.annotations.NotNull;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：旨在改进接口友好性
 */
public final class EyesPlayer2 implements LifecycleObserver {
    private static final String TAG = "EyesPlayer===>";

    public static _2DBuilder create2D(){
        return new _2DBuilder();
    }

    private static class Builder {

        private String mPath;
        private LifecycleOwner mOwner;
        private Object mStateObserver;
        private PlayerEngine mEngine;

        public <T extends Builder> T setPath(String path) {
            mPath = path;
            return (T) this;
        }

        public <T extends Builder> T setOwner(LifecycleOwner owner) {
            mOwner = owner;
            return (T) this;
        }

        public void setStateObserver(Object stateObserver) {
            mStateObserver = stateObserver;
        }

        public void setEngine(PlayerEngine engine) {
            mEngine = engine;
        }

        public PlayerController create(){
            return this.mEngine;
        }
    }

    private static class _2DBuilder extends Builder {
        private SurfaceView mSurfaceView;
        public void setSurfaceView(SurfaceView sf){
            mSurfaceView=sf;
        }

    }

    private static class _3DBuilder{
        private GLSurfaceView mGLSurfaceView;

    }
}
