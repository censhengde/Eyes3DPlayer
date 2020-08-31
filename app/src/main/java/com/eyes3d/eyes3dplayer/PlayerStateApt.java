package com.eyes3d.eyes3dplayer;

import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：PlayerState注解处理器
 */
public final class PlayerStateApt {
    private static final String TAG="PlayerStateApt===>";
    @NonNull
    final WeakReference<Object> oWnerRef;

    private Method onCompletionM, onBufferingStartM, onBufferingEndM, onPreparedM, onVideoSizeChangedM, onErrorM;

    public PlayerStateApt(Object observer) {
        this.oWnerRef = new WeakReference<>(observer);
        Class clz = oWnerRef.get().getClass();
        Method[] methods = clz.getMethods();//要求注解方法必须是pubic
        for (Method m : methods) {
            PlayerState annoState = m.getAnnotation(PlayerState.class);
            if (annoState != null) {
                State state = annoState.state();
                switch (state) {
                    case ON_PREPARED:
                        onPreparedM = m;
                        break;
                    case ON_COMPLETION:
                        onCompletionM = m;
                        break;
                    case ON_BUFFERING_START:
                        onBufferingStartM = m;
                        break;
                    case ON_BUFFERING_END:
                        onBufferingEndM = m;
                        break;
                    case ON_VIDEO_SIZE_CHANGED:
                        onVideoSizeChangedM = m;
                        break;
                    case ON_ERROR:
                        onErrorM = m;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    void invokeOnCompletion(IPlayerEngine engine) {
        invokeTargetMethod(onCompletionM, engine);
    }

    void invokeOnBufferingStart(IPlayerEngine engine) {
        invokeTargetMethod(onBufferingStartM, engine);
    }

    void invokeOnBufferingEnd(IPlayerEngine engine, int currentPosition) {
        invokeTargetMethod(onBufferingEndM, engine,currentPosition);
    }

    private void invokeTargetMethod(Method target, Object... args) {
        if (target != null) {
            if (!target.isAccessible()) {
                target.setAccessible(true);
            }
            try {
                target.invoke(oWnerRef.get(), args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
               throw new RuntimeException(TAG+"异常");
            }
        }
    }

    void invokeOnPrepared(IPlayerEngine engine) {
        invokeTargetMethod(onPreparedM, engine);
    }

    void invokeOnVideoSizeChanged(int width, int height) {
        invokeTargetMethod(onVideoSizeChangedM, width, height);
    }

    public void invokeOnError(IPlayerEngine engine,int err) {
        invokeTargetMethod(onErrorM, engine,err);
    }
}
