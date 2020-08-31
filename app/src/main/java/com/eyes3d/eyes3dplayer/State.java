package com.eyes3d.eyes3dplayer;

/**
 * Shengde·Cen on 2020/8/14
 * 说明：枚举播放器生命周期所有状态
 */
public enum State {
    ON_PREPARED,/*准备完成*/
    ON_COMPLETION,/*播放完毕*/
    ON_BUFFERING_START,/*开始缓冲*/
    ON_BUFFERING_END,/*缓冲结束*/
    ON_BUFFERING_UPDATE,/*缓冲更新*/
    ON_VIDEO_SIZE_CHANGED,/*尺寸更改*/
    ON_ERROR/*播放错误*/
}
