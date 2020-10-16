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

    /*将系统默认音量调节范围放大到0--100*/
    private static final int MAX_SEEK_BAR_VALUE = 100;
    /*SeekBar 刻度*/
    private static final int SCALE_SEEK_BAR_VALUE = 3;

    private static final boolean DEBUG=true;

    private void myLog(String msg){
        if (DEBUG){
            Log.e("EyesVolumeBar",msg);
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
        mSeekBar.setMax(MAX_SEEK_BAR_VALUE);
        mSeekBar.setProgress(volumeToProgress(mCurrentVolume));


    }

    private int volumeToProgress(int currentVolume) {
        return (currentVolume / mMaxVolume) * MAX_SEEK_BAR_VALUE;
    }

    private int progressToVolume(int progress) {
        return (progress / MAX_SEEK_BAR_VALUE) * mMaxVolume;
    }


    /*调节手势*/
    void onAdjustGesture(MotionEvent e1, MotionEvent e2, int parentHeight, float distanceY) {
        final SeekBar seekBar = mSeekBar;
        /*将整个播放界面高度MAX_SEEK_BAR_VALUE等分*/
        final float scale = distanceY / parentHeight;
        final float offsetProgress = scale * MAX_SEEK_BAR_VALUE;
//        if (Math.abs(distanceY) > scale) {
//            /*每滑动一个灵敏度距离音量+1*/
//            final int offset = (int) (distanceY / mSensitivity);
//            mCurrentVolume += offset;
//        }
        int newProgress = (int) (seekBar.getProgress() + offsetProgress);
        if (newProgress >= 0 && newProgress <= MAX_SEEK_BAR_VALUE) {
            seekBar.setProgress(newProgress);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        EyesLog.e(this, "progress =" + progress);
        final float volumeScale = (float) (mMaxVolume) / MAX_SEEK_BAR_VALUE;
        final int remainder = (progress % 10) % 2;
        switch (remainder){
            case 0:{

            }
            case 1:{

            }
        }

        if (mAudioManager != null) {
            mAudioManager.setStreamVolume(mCurrentVolume);
            mCurrentVolume++;
        }

    }

}
