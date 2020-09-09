package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Surface;

import androidx.lifecycle.LifecycleOwner;

import com.eyes3d.eyes3dplayer.Base3DRenderer;
import com.eyes3d.eyes3dplayer.Eyes3DRenderer;
import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.PlayerController;

/**
 * Shengde·Cen on 2020/8/20
 * 说明：
 */
public class Eyes3DVedioView extends BaseVideoView<GLSurfaceView> {
    private Eyes3DRenderer mRenderer;

    public Eyes3DVedioView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected GLSurfaceView setPlayView(Context context) {
        return new GLSurfaceView(context);
    }

    public void setRenderer(Eyes3DRenderer renderer) {
        this.mRenderer = renderer;
    }

    @Override
    protected PlayerController initPlayer(LifecycleOwner owner, GLSurfaceView playView, String path) {
        checkNotNull(mRenderer, "initPlayer之前请先调用setRenderer（）");
        if (mEngine == null) {
            return EyesPlayer.create3D(owner, this, playView, mRenderer, path);
        } else {
            return EyesPlayer.create3D(mEngine, owner, this, playView, mRenderer, path);
        }
    }
}
