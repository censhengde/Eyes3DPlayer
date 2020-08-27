package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：系统原生播放引擎
 */
final class SystemPlayerEngine implements IPlayerEngine {
    private static String TAG = "SystemPlayerEngine===>";
    private MediaPlayer mPlayer;
    private PlayerStateApt mApt;
    private Context mContext;

    SystemPlayerEngine(Context context) {
        mContext = context;
        mApt = new PlayerStateApt(context);
        initPlayer();
    }

    private void initPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //准备完成
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mApt.invokeOnPrepared();
            }
        });
        // 播放完毕
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mApt.invokeOnCompletion();
            }
        });
        //尺寸变化
        mPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                mApt.invokeOnVideoSizeChanged(width, height);
            }
        });
//        if (mView instanceof GLSurfaceView) {
//            mPlayer.setSurface(mView.gets);
//        }

    }

    @Override
    public void setDataSource(String path) {
        try {
            if (path.contains("https://") || path.contains("http://") || path.contains("content://")
                    || path.contains("/storage/")) {
                Uri uri = Uri.parse(path);
                mPlayer.setDataSource(mContext, uri);
                Log.e("wangguojing", "initMediaPlayer 222222223 uri=" + uri);
            } else {
                AssetManager assetMg = mContext.getAssets();
                AssetFileDescriptor fileDescriptor = assetMg.openFd(path);
                mPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            }
        } catch (IOException e) {
            Log.e(TAG, "setDataSource 异常");
            e.printStackTrace();
        }

    }

    @Override
    public void prepareAsync() {
        mPlayer.prepareAsync();
    }

    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    @Override
    public void stop() {
        mPlayer.stop();
    }

    @Override
    public void reset() {
        mPlayer.reset();
    }

    @Override
    public void release() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;

        }
    }

    @Override
    public boolean isPlaying() {
        if (mPlayer != null) {
            return mPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public void seekTo(int msec) throws IllegalStateException {
        mPlayer.seekTo(msec);
    }

    @Override
    public void setSurface(Surface surface) {
        mPlayer.setSurface(surface);
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        mPlayer.setDisplay(holder);
    }
}
