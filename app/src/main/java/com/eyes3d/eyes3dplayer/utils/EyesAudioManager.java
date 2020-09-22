package com.eyes3d.eyes3dplayer.utils;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;

/**
 * Shengde·Cen on 2020/9/21
 * 说明：
 */
public class EyesAudioManager {
    private AudioManager mAudioManager;
    private Context mContext;

    public EyesAudioManager(Context context) {
        mContext = context;
        mAudioManager = (AudioManager) mContext.getSystemService(Service.AUDIO_SERVICE);
    }

    public int getStreamMaxVolume() {
        if (mAudioManager != null) {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        } else {
            return 0;
        }
    }


    public void setStreamVolume(int volume) {
        if (mAudioManager != null){
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
        }
    }
}
