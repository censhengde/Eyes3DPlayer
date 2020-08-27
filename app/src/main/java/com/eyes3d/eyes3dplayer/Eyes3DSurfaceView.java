package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Surface;

/**
 * Shengde·Cen on 2020/8/20
 * 说明：将播放界面的各种控件（如暂停、快进、进度条等）统一封装进来，
 */
public class Eyes3DSurfaceView extends GLSurfaceView {
    private Surface mSurface;
   private Base3DRenderer mBase3DRenderer;
    public Eyes3DSurfaceView(Context context) {
        this(context, null);
    }

    public Eyes3DSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setEGLContextClientVersion(2);
        this.setRenderMode(RENDERMODE_WHEN_DIRTY);/*按需渲染*/


    }

    public Surface getSurface() {
        return mSurface;
    }

    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
//        if (renderer instanceof BaseEyes3DRenderer){
//            mBaseEyes3DRenderer=(BaseEyes3DRenderer)renderer;
//        }
    }
}
