package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.EyesAudioManager;
import com.eyes3d.eyes3dplayer.utils.EyesLog;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：
 */

final class EyesVolumeBar extends EyesAdjustBar {

    private final EyesAudioManager mAudioManager;
    private int mMaxVolume;

    /*SeekBar 刻度*/
    private static final int SCALE_SEEK_BAR_VALUE = 3;

    private static final boolean DEBUG = true;

    private void myLog(String msg) {
        if (DEBUG) {
            Log.e("EyesVolumeBar", msg);
        }
    }

    private int mCurrentVolume;

    public EyesVolumeBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAudioManager = new EyesAudioManager(context);
        mIcon.setImageResource(R.mipmap.ic_horn);
        mIcon.setColorFilter(Color.WHITE);
        mMaxVolume = mAudioManager.getStreamMaxVolume();
        mCurrentVolume = mAudioManager.getStreamVolume();
        mSeekBar.setMax(mMaxSeekBarValue);
        mSeekBar.setProgress(volumeToProgress(mCurrentVolume));


    }

    private int volumeToProgress(int currentVolume) {
        return (currentVolume / mMaxVolume) * mMaxSeekBarValue;
    }

    private int progressToVolume(int progress) {
        return (progress / mMaxSeekBarValue) * mMaxVolume;
    }


//    /*调节手势*/
//    void onAdjustGesture(MotionEvent e1, MotionEvent e2, int parentHeight, float distanceY) {
//        super.onAdjustGesture(e1, e2, parentHeight, distanceY);
//    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        super.onProgressChanged(seekBar, progress, fromUser);
        EyesLog.e(this, "progress =" + progress);
        /*单位音量对应的进度*/
        final float unit = (float) seekBar.getMax() / (float) (mMaxVolume);
        /*因为unit精度问题，可能导致（int）unit*mMaxVolume小于seekBar.getMax()，所以要适当加长
         * seekBar.getMax()方能使音量加满*/
        myLog("(int) unit="+(int) unit);
        int longerMax = progress + (int) unit * 2;
        if (longerMax % (int) unit == 0) {
            mCurrentVolume++;
            myLog("mCurrentVolume="+mCurrentVolume);
            if (mCurrentVolume < 0) {
                mCurrentVolume = 0;
            }
            if (mCurrentVolume > mMaxVolume) {
                mCurrentVolume = mMaxVolume;
            }
//            if (mAudioManager != null) {
//                mAudioManager.setStreamVolume(mCurrentVolume);
//            }
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
    }
}
