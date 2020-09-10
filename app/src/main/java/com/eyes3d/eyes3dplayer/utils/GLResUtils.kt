@file:JvmName("GLResUtils")

package com.eyes3d.eyes3dplayer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLUtils.texImage2D
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.RuntimeException

/**
 * 岑胜德 on 2020/7/22
 * 说明：由于opengl内部对异常不采取中断程序的处理，这样不方便我们定位异常，
 *     故我们封装函数并主动捕获异常。
 */
private val TAG = "getShaderCode"

//读取着色器代码
fun getShaderCode(context: Context, resId: Int): String {
    return try {
        val builder = StringBuilder()
        val inputStreams = context.resources.openRawResource(resId)
        val reader = InputStreamReader(inputStreams)
        val bufferedReader = BufferedReader(reader)
        var nextLine: String?
        do {
            nextLine = bufferedReader.readLine()
            if (nextLine == null) {
                bufferedReader.close()
                reader.close()
                inputStreams.close()
                break
            }
            builder.append(nextLine)
            builder.append('\n')
        } while (true)
        builder.toString()
    } catch (err: Exception) {
        Log.e(TAG, "异常：$err")
        " "
    }

}





fun getResBitmap(context: Context,resId:Int):Bitmap{
    val option = BitmapFactory.Options()
    option.inScaled = false
    val bitmap = BitmapFactory.decodeResource(context.resources, resId, option)
            ?: throw RuntimeException("加载纹理bitmap失败")
    return bitmap
}

