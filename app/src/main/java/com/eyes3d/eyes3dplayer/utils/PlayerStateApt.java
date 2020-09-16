package com.eyes3d.eyes3dplayer.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private final List<Object> mObservers = new ArrayList<>();
    private final SimpleArrayMap<Object, SimpleArrayMap<State, Method>> mObserver_Methods = new SimpleArrayMap<>();


    public void addObserver(Object observer) {
        if (observer == null) return;
        mObservers.add(observer);
        resolveObserver();
    }


    private void resolveObserver() {
        if (mObservers.size() == 0) return;
        for (Object observer : mObservers) {
            final SimpleArrayMap<State, Method> targetMethods = new SimpleArrayMap<>();
            Class<?> clz = observer.getClass();
            Method[] methods = clz.getMethods();//要求注解方法必须是pubic
            for (Method m : methods) {
                PlayerState annoState = m.getAnnotation(PlayerState.class);
                if (annoState != null) {
                    final State state = annoState.state();
                    switch (state) {
                        case ON_CREATE:
                            targetMethods.put(state, m);
                            break;
                        case ON_PREPARED:
                            targetMethods.put(state, m);
                            break;
                        case ON_START:
                            targetMethods.put(state, m);
                            break;
                        case ON_PAUSE:
                            targetMethods.put(state, m);
                            break;
                        case ON_STOP:
                            targetMethods.put(state, m);
                            break;
                        case ON_COMPLETION:
                            targetMethods.put(state, m);
                            break;
                        case ON_BUFFERING_START:
                            targetMethods.put(state, m);
                            break;
                        case ON_BUFFERING_END:
                            targetMethods.put(state, m);
                            break;
                        case ON_BUFFERING_UPDATE:
                            targetMethods.put(state, m);
                            break;
                        case ON_VIDEO_SIZE_CHANGED:
                            targetMethods.put(state, m);
                            break;
                        case ON_ERROR:
                            targetMethods.put(state, m);
                            break;
                        default:
                            break;
                    }
                }
            }

            mObserver_Methods.put(observer, targetMethods);

        }


    }

    private void dispatchStateEvent(State state, Object... args) {
        if (mObservers.size() == 0 || mObserver_Methods.isEmpty()) return;
        for (Object observer : mObservers) {
            try {
                final Method targetM = mObserver_Methods.get(observer).get(state);
                invokeTargetMethod(observer, targetM, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void invokeOnCreate() {
//        invokeTargetMethod(mOnCreateM);
        dispatchStateEvent(State.ON_CREATE);
    }

    public void invokeOnCompletion(PlayerController playerController) {
//        invokeTargetMethod(mOnCompletionM, playerController);
        dispatchStateEvent(State.ON_COMPLETION, playerController);
    }

    public void invokeOnBufferingStart(PlayerController playerController) {
        dispatchStateEvent(State.ON_BUFFERING_START, playerController);
    }

    public void invokeOnBufferingEnd(PlayerController playerController, long currentPosition) {
        dispatchStateEvent(State.ON_BUFFERING_END, playerController,currentPosition);
    }

    private void invokeTargetMethod(@Nullable Object observer, @Nullable Method target, Object... args) {
        if (target != null) {
            if (!target.isAccessible()) {
                target.setAccessible(true);
            }
            try {
                target.invoke(observer, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(TAG + "异常");
            }
        }
    }

    public void invokeOnPrepared(PlayerController playerController) {
//        invokeTargetMethod(mOnPreparedM, playerController);
        dispatchStateEvent(State.ON_PREPARED, playerController);
    }

    public void invokeOnBufferingUpdata(PlayerController playerCtrl) {
//        invokeTargetMethod(mOnBufferingUpdateM, playerCtrl);
        dispatchStateEvent(State.ON_BUFFERING_UPDATE, playerCtrl);
    }

    public void invokeOnVideoSizeChanged(PlayerController playerCtrl, int width, int height) {
//        invokeTargetMethod(mOnVideoSizeChangedM, playerCtrl, width, height);
        dispatchStateEvent(State.ON_VIDEO_SIZE_CHANGED, playerCtrl, width, height);
    }

    public void invokeOnError(PlayerController playerController, int err) {
//        invokeTargetMethod(mOnErrorM, playerController, err);
        dispatchStateEvent(State.ON_ERROR, playerController, err);
    }

    public void invokeOnStart() {
//        invokeTargetMethod(mOnStartM);
        dispatchStateEvent(State.ON_START);
    }

    public void invokeOnPause() {
//        invokeTargetMethod(mOnPauseM);
        dispatchStateEvent(State.ON_PAUSE);
    }

    public void invokeOnStop() {
//        invokeTargetMethod(mOnStopM);
        dispatchStateEvent(State.ON_STOP);
    }

}
