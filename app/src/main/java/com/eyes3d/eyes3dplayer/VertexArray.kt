package com.eyes3d.common.util

import android.opengl.GLES20.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.properties.Delegates

/**
 * shengde·cen on 2020/7/27
 * 说明：顶点数据统一管理类
 */
class VertexArray(vertData: FloatArray) {
    private var mFloatBuffer: FloatBuffer by Delegates.notNull()

    init {
        mFloatBuffer = ByteBuffer.allocateDirect(vertData.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertData)
    }

    fun vertexAttribPointer(
            indx: Int,
            offset: Int,
            size: Int,
            stride: Int
    ) {
        mFloatBuffer.position(offset)
        glVertexAttribPointer(indx, size, GL_FLOAT, false, stride, mFloatBuffer)
        glEnableVertexAttribArray(indx)
    }
}