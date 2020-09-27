package com.eyes3d.eyes3dplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.AnimationUtils;
import com.eyes3d.eyes3dplayer.utils.EyesLog;
import com.eyes3d.eyes3dplayer.utils.FormatUtils;

import static android.media.MediaPlayer.SEEK_CLOSEST;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：
 */
public class EyesVideoBottomLayout extends FloatView {
    /*播放进度条*/
    private SeekBar mSeekBar;
    /*播放暂停*/
    private Button mBtnPlayAndPause;
    private boolean mPlaying = false;
    private TextView mTvCurrentTime, mTvVideoDuration;
    private PlayerController mPlayerController;
    private UpdateSeekBarRunnable mUpdateSeekBarRunnable;
    private int mPlayViewWidth,
            mPlayViewHeight;


    public EyesVideoBottomLayout(Context context) {
        super(context);
    }

    @SuppressLint("ClickableViewAccessibility")
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
        super.setFocusable(false);
        super.setClickable(false);
        mBtnPlayAndPause = findViewById(R.id.btn_video_bottom_layout_play_pause);
        mSeekBar = findViewById(R.id.seekbar_video_bottom_layout_progress_bar);
        mTvCurrentTime = findViewById(R.id.tv_video_bottom_layout_video_current_titme);
        mTvVideoDuration = findViewById(R.id.tv_video_bottom_layout_video_total_time);

        mUpdateSeekBarRunnable = new UpdateSeekBarRunnable();


    }

    public void setPlayViewWidth(int playViewWidth) {
        mPlayViewWidth = playViewWidth;
    }

    public void setPlayViewHeight(int playViewHeight) {
        mPlayViewHeight = playViewHeight;
    }


    void onStartPlay() {
        mPlaying = true;
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_play);
        startUpdateSeekBarTask();
//        mSeekBar.setProgress((int) mPlayerController.getCurrentPosition());
    }

    void onPlayPause() {
        mPlaying = false;
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_stop);
        removeUpdateSeekBarTask();
    }


    /*播放器准备完成*/
    public void onPlayerPrepared(PlayerController controller) {
        mPlayerController = controller;
        mBtnPlayAndPause.setOnClickListener((v) -> {
            if (mPlaying) {
                mPlaying = false;
                controller.pause();
            } else {
                mPlaying = true;
                controller.start();
            }

        });

        mSeekBar.setMax((int) controller.getDuration());
        mTvVideoDuration.setText(FormatUtils.mssToString(controller.getDuration()));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /*fromUser:当调用setProgress（）时是false，手动拖拽时是true*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvCurrentTime.setText(FormatUtils.mssToString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                removeUpdateSeekBarTask();
                EyesLog.e(this, "seekBar.getProgress()=:" + seekBar.getProgress());
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                EyesLog.e(EyesVideoBottomLayout.this, "seekBar.getProgress()=:" + seekBar.getProgress());
                controller.seekTo(seekBar.getProgress(), SEEK_CLOSEST);
                startUpdateSeekBarTask();
                EyesLog.e(EyesVideoBottomLayout.this, "controller.getCurrentPosition()=:" + mPlayerController.getCurrentPosition());
                performClick();
            }
        });

    }


    /*水平滑动快进/快退*/
    void onHorizontalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        final float scrollOffset = (e2.getX() - e1.getX());

        //根据移动的正负决定快进还是快退
        float progressOffset = 0;
        if (mPlayViewWidth > 0) {
            progressOffset = Math.abs(scrollOffset / mPlayViewWidth * mSeekBar.getMax());
        }
        if (scrollOffset > 0) {
            mSeekBar.setProgress((int) (mSeekBar.getProgress() + progressOffset * 100));
        } else {
            mSeekBar.setProgress((int) (mSeekBar.getProgress() - progressOffset * 100));
        }
    }

    /*更新进度条*/
    private class UpdateSeekBarRunnable implements Runnable {

        @Override
        public void run() {
            if (mPlayerController == null) {
                return;
            }
            final long duration = mPlayerController.getDuration();
            final long progress = mPlayerController.getCurrentPosition();
            mSeekBar.setProgress((int) progress);
            EyesLog.e(this, "progress=" + progress + " duration= " + duration);
            if (progress < duration) {
                postDelayed(mUpdateSeekBarRunnable, 500);
            }

        }
    }

    @Override
    public void show() {
        super.show();
        if (mPlayerController != null && mPlayerController.isPlaying()) {
            startUpdateSeekBarTask();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mPlayerController != null) {

            final long duration = mPlayerController.getDuration();
            long progress = mPlayerController.getCurrentPosition();
            while (true) {
                if (progress >= duration) {
                    removeUpdateSeekBarTask();
                    break;
                }
               progress=mPlayerController.getCurrentPosition();
            }
        }
    }

    private void removeUpdateSeekBarTask() {
        super.removeCallbacks(mUpdateSeekBarRunnable);
    }

    private void startUpdateSeekBarTask() {
        super.postDelayed(mUpdateSeekBarRunnable, 100);
    }
}
