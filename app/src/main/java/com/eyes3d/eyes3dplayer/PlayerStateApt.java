package com.eyes3d.eyes3dplayer;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：注解处理器
 */
class PlayerStateApt {
    @NonNull
    final WeakReference<Object> oWnerRef;

    private Method onCompletionM, onBufferingStartM, onBufferingEndM, onPreparedM, onVideoSizeChangedM;

    public PlayerStateApt(Object oWner) {
        this.oWnerRef = new WeakReference<>(oWner);
        Class clz = oWnerRef.get().getClass();
        Method[] methods = clz.getMethods();
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
                    default:
                        break;
                }
            }
        }
    }

    void invokeOnCompletion() {
        invokeTargetMethod(onCompletionM);
    }

    void invokeOnBufferingStart() {
        invokeTargetMethod(onBufferingStartM);
    }

    void invokeOnBufferingEnd() {
        invokeTargetMethod(onBufferingEndM);
    }

    private void invokeTargetMethod(Method target, Object... args) {
        if (target != null && !target.isAccessible()) {
            target.setAccessible(true);
            try {
                target.invoke(oWnerRef.get(), args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    void invokeOnPrepared() {
        invokeTargetMethod(onPreparedM);
    }

    void invokeOnVideoSizeChanged(int width, int height) {
        invokeTargetMethod(onVideoSizeChangedM, width, height);
    }
}
