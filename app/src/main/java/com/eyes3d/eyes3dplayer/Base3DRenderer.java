package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.Surface;

import androidx.annotation.NonNull;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Shengde·Cen on 2020/8/24
 * 说明：
 */
public class Base3DRenderer implements IEyes3DRenderer, SurfaceTexture.OnFrameAvailableListener {

    private SurfaceTexture mSurfaceTexture;
    private GLSurfaceView mGLSurfaceView;
    private final Object mLock = new Object();
    protected Context mContext;
    protected int mVideoTextureId;
    @SuppressWarnings("允许子类访问")
    protected String mVertShaderCode;

    protected String mFragShaderCode;

    /*在UI线程中*/
    @Override
    public @NonNull
    Surface getSurface() {
        synchronized (mLock){
        if (mSurface == null) {
            try {
                mLock.wait();//等待mSurface初始化完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }
        return mSurface;
    }

    private Surface mSurface;

    public Base3DRenderer(GLSurfaceView glSfview, int vertShaderRes, int fragShaderRes) {
        this(glSfview, GLResUtils.getShaderCode(glSfview.getContext(), vertShaderRes),
                GLResUtils.getShaderCode(glSfview.getContext(), fragShaderRes));
    }

    public Base3DRenderer(GLSurfaceView glSfview, String vertShaderCode, String fragShaderCode) {
        mGLSurfaceView = glSfview;
        mContext = mGLSurfaceView.getContext().getApplicationContext();
        mVertShaderCode = vertShaderCode;
        mFragShaderCode = fragShaderCode;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //不必重复


    }


    /*在GL线程中*/
    protected int getVideoTexture() {
        synchronized (mLock) {
            int id = 0;
            mSurfaceTexture = new SurfaceTexture(id);
            mSurfaceTexture.setOnFrameAvailableListener(this);
            mSurface = new Surface(mSurfaceTexture);
            mSurface.release();
            int[] textures = new int[1];
            GLES20.glGenTextures(1, textures, 0);
            id = textures[0];
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, id);
            mLock.notify();
            return id;
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mSurfaceTexture.updateTexImage();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mGLSurfaceView.requestRender();/*有一帧渲染一帧*/
    }
}
