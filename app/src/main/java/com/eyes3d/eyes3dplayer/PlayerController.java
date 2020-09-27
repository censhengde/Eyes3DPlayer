package com.eyes3d.eyes3dplayer;

import androidx.annotation.RequiresApi;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：播放控制器，直接面向应用层提供接口，相比IplayerEngine屏蔽更多具体细节，层次更抽象。
 */
public interface PlayerController {

    void start();

    void pause();

    void stop();

    void reset();
    boolean isPlaying();
    long getCurrentPosition();

    long getDuration();

    void seekTo(int msec);
    @RequiresApi(26)
    void seekTo(int msec, int mode);


}
