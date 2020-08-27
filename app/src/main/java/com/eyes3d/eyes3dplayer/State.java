package com.eyes3d.eyes3dplayer;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：
 */
public enum State {
    ON_PREPARED,
    ON_COMPLETION,
    ON_BUFFERING_START,/*开始缓冲*/
    ON_BUFFERING_END,/*缓冲结束*/
    ON_VIDEO_SIZE_CHANGED
}
