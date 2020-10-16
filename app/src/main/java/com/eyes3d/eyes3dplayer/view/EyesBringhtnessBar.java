package com.eyes3d.eyes3dplayer.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.ScreenManager;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：屏幕亮度调节条
 */
 final class EyesBringhtnessBar extends EyesAdjustBar{
    private Activity mCurrentActivity;

    void setCurrentActivity(Activity activity) {
        this.mCurrentActivity = activity;
    }


    public EyesBringhtnessBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mIcon.setImageResource(R.mipmap.ic_bringhtness);
        mIcon.setColorFilter(Color.WHITE);
        mSeekBar.setMax(255);
        mSeekBar.setProgress(ScreenManager.getScreenBrightness(context));
    }

    @Override
    void onAdjustGesture(MotionEvent e1, MotionEvent e2, int parentHeight, float distanceY) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              if (mCurrentActivity!=null){
                  ScreenManager.setScreenBringhtness(mCurrentActivity,progress);
              }
    }
}
