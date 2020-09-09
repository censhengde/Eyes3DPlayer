package com.eyes3d.eyes3dplayer;

import android.opengl.GLSurfaceView;
import android.view.Surface;

/**
 * Shengde·Cen on 2020/8/25
 * 说明：必须对外提供一个Surface
 */
public interface Eyes3DRenderer extends GLSurfaceView.Renderer {
    Surface getSurface();
}
