package com.eyes3d.eyes3dplayer.utils;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;

import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.State;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：PlayerState注解处理器
 */
public final class PlayerStateApt {
    private static final String TAG = "PlayerStateApt===>";
    @NonNull
    private  WeakReference<Object> mOWnerRef;
    private final List<Object> mObservers=new ArrayList<>();
    private final SimpleArrayMap<Object,List<Method>> mTargetMethods=new SimpleArrayMap<>();

    private Method mOnCreateM, mOnCompletionM, mOnBufferingStartM,
            mOnBufferingEndM, mOnPreparedM, mOnVideoSizeChangedM,
            mOnBufferingUpdateM, mOnErrorM, mOnStartM, mOnPauseM, mOnStopM;


    public void  addObserver(Object observer) {
        if (observer==null) return;
        mObservers.add(observer);
        this.mOWnerRef = new WeakReference<>(observer);
        Class<?> clz = mOWnerRef.get().getClass();
        Method[] methods = clz.getMethods();//要求注解方法必须是pubic
        for (Method m : methods) {
            PlayerState annoState = m.getAnnotation(PlayerState.class);
            if (annoState != null) {
                State state = annoState.state();
                switch (state) {
                    case ON_CREATE:
                        mOnCreateM = m;
                        break;
                    case ON_PREPARED:
                        mOnPreparedM = m;
                        break;
                    case ON_START:
                        mOnStartM = m;
                        break;
                    case ON_PAUSE:
                        mOnPauseM = m;
                        break;
                    case ON_STOP:
                        mOnStopM = m;
                        break;
                    case ON_COMPLETION:
                        mOnCompletionM = m;
                        break;
                    case ON_BUFFERING_START:
                        mOnBufferingStartM = m;
                        break;
                    case ON_BUFFERING_END:
                        mOnBufferingEndM = m;
                        break;
                    case ON_BUFFERING_UPDATE:
                        mOnBufferingUpdateM = m;
                        break;
                    case ON_VIDEO_SIZE_CHANGED:
                        mOnVideoSizeChangedM = m;
                        break;
                    case ON_ERROR:
                        mOnErrorM = m;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void invokeOnCreate() {
        invokeTargetMethod(mOnCreateM);
    }

    public void invokeOnCompletion(PlayerController playerController) {
        invokeTargetMethod(mOnCompletionM, playerController);
    }

    public void invokeOnBufferingStart(PlayerController playerController) {
        invokeTargetMethod(mOnBufferingStartM, playerController);
    }

    public void invokeOnBufferingEnd(PlayerController playerController, long currentPosition) {
        invokeTargetMethod(mOnBufferingEndM, playerController, currentPosition);
    }

    private void invokeTargetMethod(Method target, Object... args) {
        if (target != null) {
            if (!target.isAccessible()) {
                target.setAccessible(true);
            }
            try {
                target.invoke(mOWnerRef.get(), args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(TAG + "异常");
            }
        }
    }

    public void invokeOnPrepared(PlayerController playerController) {
        invokeTargetMethod(mOnPreparedM, playerController);
    }

    public void invokeOnBufferingUpdata(PlayerController playerCtrl) {
        invokeTargetMethod(mOnBufferingUpdateM, playerCtrl);
    }

    public void invokeOnVideoSizeChanged(PlayerController playerCtrl, int width, int height) {
        invokeTargetMethod(mOnVideoSizeChangedM, playerCtrl, width, height);
    }

    public void invokeOnError(PlayerController playerController, int err) {
        invokeTargetMethod(mOnErrorM, playerController, err);
    }

    public void invokeOnStart() {
        invokeTargetMethod(mOnStartM);
    }

    public void invokeOnPause() {
        invokeTargetMethod(mOnPauseM);
    }

    public void invokeOnStop() {
        invokeTargetMethod(mOnStopM);
    }

}
