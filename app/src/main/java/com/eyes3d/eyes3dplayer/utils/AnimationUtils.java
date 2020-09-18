package com.eyes3d.eyes3dplayer.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;

/**
 * Shengde·Cen on 2020/9/18
 * 说明：
 */
public final class AnimationUtils {
    private AnimationUtils() {
    }

    public static @NonNull
    Animation getTranslateAnimation(float fromXValue, float toXValue, float fromYValue, float toYValue,int duration) {
        final Animation animaton = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                fromXValue,
                Animation.RELATIVE_TO_SELF,
                toXValue,
                Animation.RELATIVE_TO_SELF,
                fromYValue,
                Animation.RELATIVE_TO_SELF,
                toYValue);
        animaton.setRepeatMode(Animation.REVERSE);
        animaton.setDuration(duration);
        return animaton;
    }
}
