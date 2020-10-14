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
import android.widget.ImageView;
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
public final class EyesVideoBottomLayout extends FloatView {
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
    private long mVideoDuration;
    private TextView mTvProgress;

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
    }

    void onPlayPause() {
        mPlaying = false;
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_stop);
        removeUpdateSeekBarTask();
    }


    /*播放器准备完成*/
    public void onPlayerPrepared(PlayerController controller) {
        mPlayerController = controller;
        if (mPlayerController == null) return;
        mBtnPlayAndPause.setOnClickListener((v) -> {
            if (mPlaying) {
                mPlaying = false;
                controller.pause();
            } else {
                mPlaying = true;
                controller.start();
            }

        });
        mVideoDuration = controller.getDuration();
        mSeekBar.setMax((int) mVideoDuration);
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
                EyesLog.e(this, "onStartTrackingTouch");
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                EyesLog.e(EyesVideoBottomLayout.this, "onStopTrackingTouch");
                controller.seekTo(seekBar.getProgress(), SEEK_CLOSEST);
                startUpdateSeekBarTask();
                performClick();
            }
        });

    }

    private int mCurrentPostion;

    void onDown(MotionEvent e) {
        EyesLog.e(this, "onDown");
        mCurrentPostion = mSeekBar.getProgress();
    }

    /*水平滑动快进/快退*/
    @SuppressLint("SetTextI18n")
    void onHorizontalScroll(MotionEvent e1, MotionEvent e2, TextView tvProgress, ImageView ivFF_REW, float distanceX) {
        if (mVideoDuration == 0 || tvProgress == null) {
            return;
        }
        if (mTvProgress == null) {
            mTvProgress = tvProgress;
        }
        final float scrollOffset = (e2.getX() - e1.getX());
        int newPosition = 0;
        if (Math.abs(scrollOffset) >= 50) {
            int ms = ((int) (scrollOffset / 50)) * 1000;
            newPosition = mCurrentPostion + ms;
            if (newPosition > mVideoDuration) {
                newPosition = (int) mVideoDuration;
            }
            if (newPosition < 0) {
                newPosition = 0;
            }
            mSeekBar.setProgress(newPosition);
        }
        //快退
        if (distanceX < 0) {
//            ivFF_REW.setImageResource(R.mipmap.progress_left);
        } else {
//            ivFF_REW.setImageResource(R.mipmap.progress_right);
        }
        if (mTvProgress.getVisibility() == GONE) {
            mTvProgress.setVisibility(VISIBLE);
        }
        mTvProgress.setText(FormatUtils.mssToString(newPosition) + "/" + FormatUtils.mssToString(mVideoDuration));
    }

    void onScrollUp() {
        if (mTvProgress != null && mTvProgress.getVisibility() == VISIBLE) {
            mTvProgress.setVisibility(GONE);
        }
    }

    /*更新进度条*/
    private final class UpdateSeekBarRunnable implements Runnable {
        @Override
        public void run() {
            if (mPlayerController == null) {
                return;
            }
            final long duration = mPlayerController.getDuration();
            final long progress = mPlayerController.getCurrentPosition();
            mSeekBar.setProgress((int) progress);
            //如果尚未播放完毕，则继续更新
            if (progress < duration) {
                EyesLog.e(this, "更新progress：" + progress);
                postDelayed(mUpdateSeekBarRunnable, 500);
            }
        }
    }

    @Override
    public void show() {
        super.show();
        final long duration = mPlayerController.getDuration();
        final long progress = mPlayerController.getCurrentPosition();
        if (mPlayerController != null && mPlayerController.isPlaying()
                && (progress < duration)) {
            startUpdateSeekBarTask();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void removeUpdateSeekBarTask() {
        super.removeCallbacks(mUpdateSeekBarRunnable);
    }

    private void startUpdateSeekBarTask() {
        super.postDelayed(mUpdateSeekBarRunnable, 100);
    }
}
