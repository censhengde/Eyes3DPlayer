package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：
 */
public class EyesVideoBottomLayout extends FloatView {
    /*播放进度条*/
    private SeekBar mProgressBar;
    /*播放暂停*/
    private VideoPlayAndPauseButton mBtnPlayAndPause;
    private PlayerController mPlayerController;

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
//        mProgressBar.setOnSeekBarChangeListener();
        mPlayerController.addStateObserver(this);

    }

    public void setOnPlayAndPauseListener(VideoPlayAndPauseButton.OnPlayAndPauseListener listener) {
        mBtnPlayAndPause.setOnPlayAndPauseListener(listener);
    }
}
