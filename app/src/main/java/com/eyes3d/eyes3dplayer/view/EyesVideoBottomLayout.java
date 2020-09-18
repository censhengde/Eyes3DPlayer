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
import com.eyes3d.eyes3dplayer.utils.AnimationUtils;
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
    private boolean mPlaying = false;

    public EyesVideoBottomLayout(Context context) {
        super(context);
    }

    public EyesVideoBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mShowAnimation = AnimationUtils.getTranslateAnimation(0, 0, 1, 0, 600);
        mDismissAnimation = AnimationUtils.getTranslateAnimation(0, 0, 0, 1, 600);
    }


    @Override
    protected int retRootLayout() {
        return R.layout.vedio_bottom_layout;
    }

    @Override
    protected void initView() {
        mBtnPlayAndPause = findViewById(R.id.btn_video_bottom_layout_play_pause);
        mProgressBar = findViewById(R.id.seekbar_video_bottom_layout_progress_bar);

    }

    @PlayerState(state = State.ON_START)
    void onStartPlay() {
        EyesLog.e(this, "开始播放111");
        mPlaying = true;
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_play);
    }

    void onPlayPause() {
        mPlaying = false;
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_stop);
    }

    public void onPlayerCreated(PlayerController controller) {
        mBtnPlayAndPause.setOnClickListener((v) -> {
            if (mPlaying) {
                mPlaying = false;
                controller.pause();
            } else {
                mPlaying = true;
                controller.start();
            }

        });

        mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
