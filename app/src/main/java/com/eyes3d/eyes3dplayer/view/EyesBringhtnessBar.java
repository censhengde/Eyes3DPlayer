package com.eyes3d.eyes3dplayer.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
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

    private static final boolean DEBUG = true;

    private void myLog(String msg) {
        if (DEBUG) {
            Log.e("EyesBringhtnessBar", msg);
        }
    }
    void setCurrentActivity(Activity activity) {
        this.mCurrentActivity = activity;
    }


    public EyesBringhtnessBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mIcon.setImageResource(R.mipmap.ic_bringhtness);
        mIcon.setColorFilter(Color.WHITE);
        mMaxSeekBarValue=255;
        mSeekBar.setMax(mMaxSeekBarValue);
        mSeekBar.setProgress(ScreenManager.getScreenBrightness(context));
    }

    @Override
    void onAdjustGesture(MotionEvent e1, MotionEvent e2, int parentHeight, float distanceY) {
        super.onAdjustGesture(e1, e2, parentHeight, distanceY);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        myLog("progress="+progress);
              if (mCurrentActivity!=null){
                  ScreenManager.setScreenBringhtness(mCurrentActivity,progress);
              }
    }
}
