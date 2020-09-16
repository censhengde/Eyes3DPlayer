package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.PlayerState;
import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.State;
import com.eyes3d.eyes3dplayer.utils.EyesLog;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：
 */
public class EyesVideoBottomLayout extends FloatView {
    /*播放进度条*/
    private SeekBar mProgressBar;
    /*播放暂停*/
    private Button mBtnPlayAndPause;
    PlayerController mPlayerController;
    private boolean mPlaying = false;

    private VideoPlayAndPauseButton.OnPlayAndPauseListener mOnPlayAndPauseListener;

    public void setOnPlayAndPauseListener(VideoPlayAndPauseButton.OnPlayAndPauseListener listener) {
        mOnPlayAndPauseListener = listener;
    }

    public EyesVideoBottomLayout(Context context) {
        super(context);
    }

    public EyesVideoBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mShowAnimation = initTranslateAnimation(0, 0, 1, 0);
        mDismissAnimation = initTranslateAnimation(0, 0, 0, 1);
    }


    @Override
    protected int retRootLayout() {
        return R.layout.vedio_bottom_layout;
    }

    @Override
    protected void initView() {
        mBtnPlayAndPause = findViewById(R.id.btn_video_bottom_layout_play_pause);
        mProgressBar = findViewById(R.id.seekbar_video_bottom_layout_progress_bar);
        mBtnPlayAndPause.setOnClickListener((v) -> {
            if (mOnPlayAndPauseListener == null) return;
            if (!mPlaying) {
                mPlaying = true;
                mOnPlayAndPauseListener.onClickPlay();
            } else {
                mPlaying = false;
                mOnPlayAndPauseListener.onClickPause();
            }
        });
    }

    @PlayerState(state = State.ON_START)
    public void onStartPlay() {
        EyesLog.e(this, "开始播放111");
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_play);
    }

    void onPlayPause() {
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_stop);
    }
}
