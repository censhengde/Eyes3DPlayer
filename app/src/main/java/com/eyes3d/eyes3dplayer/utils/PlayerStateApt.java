package com.eyes3d.eyes3dplayer.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.State;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：PlayerState注解处理器
 */
public final class PlayerStateApt {
    private static final String TAG = "PlayerStateApt===>";
    @NonNull
    private final WeakReference<Object> oWnerRef;

    private Method onStartedM, onCompletionM, onBufferingStartM,
            onBufferingEndM, onPreparedM, onVideoSizeChangedM, onBufferingUpdateM, onErrorM;

    public PlayerStateApt(Object observer) {
        this.oWnerRef = new WeakReference<>(observer);
        Class<?> clz = oWnerRef.get().getClass();
        Method[] methods = clz.getMethods();//要求注解方法必须是pubic
        for (Method m : methods) {
            PlayerState annoState = m.getAnnotation(PlayerState.class);
            if (annoState != null) {
                State state = annoState.state();
                switch (state) {
                    case ON_STARTED:
                        onStartedM = m;
                        break;
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
                    case ON_BUFFERING_UPDATE:
                        onBufferingUpdateM = m;
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

    public void invokeOnStarted() {
        invokeTargetMethod(onStartedM);
    }

    public void invokeOnCompletion(PlayerController playerController) {
        invokeTargetMethod(onCompletionM, playerController);
    }

    public void invokeOnBufferingStart(PlayerController playerController) {
        invokeTargetMethod(onBufferingStartM, playerController);
    }

    public void invokeOnBufferingEnd(PlayerController playerController, long currentPosition) {
        invokeTargetMethod(onBufferingEndM, playerController, currentPosition);
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
                throw new RuntimeException(TAG + "异常");
            }
        }
    }

    public void invokeOnPrepared(PlayerController playerController) {
        invokeTargetMethod(onPreparedM, playerController);
    }

    public void invokeOnBufferingUpdata(PlayerController playerCtrl) {
        invokeTargetMethod(onBufferingUpdateM, playerCtrl);
    }

    public void invokeOnVideoSizeChanged(PlayerController playerCtrl, int width, int height) {
        invokeTargetMethod(onVideoSizeChangedM, playerCtrl, width, height);
    }

    public void invokeOnError(PlayerController playerController, int err) {
        invokeTargetMethod(onErrorM, playerController, err);
    }


}
