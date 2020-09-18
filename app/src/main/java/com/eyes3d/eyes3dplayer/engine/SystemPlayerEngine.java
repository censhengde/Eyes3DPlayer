package com.eyes3d.eyes3dplayer.engine;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Map;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：系统原生播放引擎
 */
public final class SystemPlayerEngine extends AbstractPlayerEngine {
    private static String TAG = "SystemPlayerEngine===>";
    private MediaPlayer mPlayer;
    private Context mContext;

    public SystemPlayerEngine(Context context) {
        mContext = context;
    }

    protected void initPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //准备完成
        mPlayer.setOnPreparedListener(mp -> mApt.invokeOnPrepared(SystemPlayerEngine.this));
        /*返回true则处理，false则丢弃*/
        mPlayer.setOnInfoListener((mp, what, extra) -> {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    //开始缓存，暂停播放
                    mApt.invokeOnBufferingStart(SystemPlayerEngine.this);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    //缓存完成，继续播放
                    mApt.invokeOnBufferingEnd(SystemPlayerEngine.this, mPlayer.getCurrentPosition());
                    break;
                default:
                    break;
            }

            return true;
        });
        mPlayer.setOnBufferingUpdateListener((mp, percent) -> mApt.invokeOnBufferingUpdata(SystemPlayerEngine.this));
        // 播放完毕
        mPlayer.setOnCompletionListener(mp -> mApt.invokeOnCompletion(SystemPlayerEngine.this));
        //尺寸变化
        mPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                mApt.invokeOnVideoSizeChanged(SystemPlayerEngine.this, width, height);
            }
        });
        /*拦截错误：当有错误时，true表示拦截，false=不拦截，并调用OnCompletionListener*/
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mApt.invokeOnError(SystemPlayerEngine.this, what);
                return true;
            }
        });
    }

    @Override
    public void setDataSource(String path) {
        this.setDataSource(path, null);

    }


    public void setDataSource(String path, @Nullable Map<String, String> headers) {
        try {
            if (path.contains("https://") || path.contains("http://") || path.contains("content://")
                    || path.contains("/storage/")) {
                Uri uri = Uri.parse(path);
                if (headers == null) {
                    mPlayer.setDataSource(mContext, uri);
                } else {
                    mPlayer.setDataSource(mContext, uri, headers);
                }
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
        super.start();
    }

    @Override
    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            super.pause();
        }
    }

    @Override
    public void stop() {
        mPlayer.stop();
        super.stop();
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

    @Override
    public void setSurface(Surface surface) {
        mPlayer.setSurface(surface);
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        mPlayer.setDisplay(holder);
    }
}
