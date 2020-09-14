package com.eyes3d.eyes3dplayer.engine;

import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public class IjkPlayerEngine extends AbstractPlayerEngine {
    private static final String TAG = "IjkPlayerEngine=======>";
    private IjkMediaPlayer mPlayer;

    /*播放器初始化、各种配置*/
    protected void initPlayer() {
        mPlayer = new IjkMediaPlayer();
        mPlayer.setOnPreparedListener(iMediaPlayer -> mApt.invokeOnPrepared(IjkPlayerEngine.this));
        mPlayer.setOnCompletionListener(iMediaPlayer -> mApt.invokeOnCompletion(IjkPlayerEngine.this));

        mPlayer.setOnInfoListener((iMediaPlayer, i, i1) -> {
            switch (i) {
                case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    mApt.invokeOnBufferingStart(IjkPlayerEngine.this);
                    break;
                case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    mApt.invokeOnBufferingEnd(IjkPlayerEngine.this, iMediaPlayer.getCurrentPosition());
                    break;
            }
            return true;
        });


    }


    @Override
    public void setDataSource(String path) {
        try {
            mPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataSource(String path, Map<String, String> headers) {
        try {
            mPlayer.setDataSource(path, headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareAsync() {
        mPlayer.prepareAsync();
    }

    @Override
    public void release() {
        if (mPlayer != null) {
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void setSurface(Surface surface) {
        mPlayer.setSurface(surface);
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        mPlayer.setDisplay(holder);
    }

    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void pause() {
        mPlayer.pause();
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
    public long getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public void seekTo(int msec) throws IllegalStateException {
        mPlayer.seekTo(msec);
    }
}
