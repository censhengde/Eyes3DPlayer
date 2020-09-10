package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import androidx.lifecycle.LifecycleOwner;

import com.eyes3d.eyes3dplayer.renderer.Eyes3DRenderer;
import com.eyes3d.eyes3dplayer.EyesPlayer;
import com.eyes3d.eyes3dplayer.PlayerController;

import static com.eyes3d.eyes3dplayer.utils.ParamsUtils.checkNotNull;

/**
 * Shengde·Cen on 2020/8/20
 * 说明：
 */
public class Eyes3DVedioView extends Eyes2DVideoView {
    private Eyes3DRenderer mRenderer;
    private GLSurfaceView mGLSurfaceView;

    @Override
    public int setContentView() {
        return 0;
    }

    public Eyes3DVedioView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected PlayerController initPlayer() {
        if (mEngine == null) {

        } else {

        }
        return null;
    }

    public void setRenderer(Eyes3DRenderer renderer) {
        this.mRenderer = renderer;
    }


}
