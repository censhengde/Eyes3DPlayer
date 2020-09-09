package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.eyes3d.common.util.VertexArray;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Shengde·Cen on 2020/8/24
 * 说明：
 */
public class Eyes3DRendererImpl extends Base3DRenderer {
    private int mProgramId;
    private int mPositionLoca;
    private int mTextureLoca;
    private int mTextureLoca2;
    private int uInputColorLoca;
    private int mTextureCoordLoca;
    private int mDisplayVideoLoca;
    private int mPurseColorLoca;
    private int mBlackWhiteTextureId;

    private static final float[] VERTEX_ARRAY = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };
    private static final float[] TEXTURE_COORD_ARRAY = {
            0.0f, 1.0f, // bottom left (V1)
            1.0f, 1.0f, // bottom right (V3)
            0.0f, 0.0f, // top left (V2)
            1.0f, 0.0f, // top right (V4)

    };
    private VertexArray mVertPositionArray;
    private VertexArray mTextureCoordArray;

    public Eyes3DRendererImpl(GLSurfaceView glSfview, int vertShaderCode, int fragShaderCode) {
        super(glSfview, vertShaderCode, fragShaderCode);
        mVertPositionArray = new VertexArray(VERTEX_ARRAY);
        mTextureCoordArray = new VertexArray(TEXTURE_COORD_ARRAY);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mProgramId = GLApiUtils.createProgram(mVertShaderCode, mFragShaderCode);
        mPositionLoca = GLES20.glGetAttribLocation(mProgramId, "aPosition");
        mTextureLoca = GLES20.glGetUniformLocation(mProgramId, "sTexture");
        mTextureLoca2 = GLES20.glGetUniformLocation(mProgramId, "sTexture2");
//        uInputColorLoca = GLES20.glGetUniformLocation(mProgramId, "uInputColor");
        mTextureCoordLoca = GLES20.glGetAttribLocation(mProgramId, "aTexCoord");
//        mDisplayVideoLoca = GLES20.glGetUniformLocation(mProgramId, "displayVideo");
//        mPurseColorLoca = GLES20.glGetUniformLocation(mProgramId, "purse_color");
       /*GLES11Ext.GL_TEXTURE_EXTERNAL_OES的用处？
      之前提到视频解码的输出格式是YUV的（YUV420p，应该是），那么这个扩展纹理的作用就是实现YUV格式到RGB的自动转化，
      我们就不需要再为此写YUV转RGB的代码了*/
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        mBlackWhiteTextureId = getBlackAndWhiteTexture(mContext);
        mVideoTextureId = getVideoTexture();
    }


    /*获取黑白图数据*/
    private byte[] getBlackAndWhiteImage(Context context) {
        return null;
    }

    private int getBlackAndWhiteTexture(Context context) {
        byte[] data = getBlackAndWhiteImage(context);
        return GLApiUtils.loadTexture(data);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        mBlackWhiteTextureId = getBlackAndWhiteTexture(mContext);/*刷新黑白图*/
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        mVertPositionArray.vertexAttribPointer(mPositionLoca, 0, 2, 8);

        mTextureCoordArray.vertexAttribPointer(mTextureCoordLoca, 0, 2, 8);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mVideoTextureId);

        GLES20.glUniform1i(mTextureLoca, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mBlackWhiteTextureId);
        GLES20.glUniform1i(mTextureLoca2, 1);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(mPositionLoca);
        GLES20.glDisableVertexAttribArray(mTextureCoordLoca);

    }
}
