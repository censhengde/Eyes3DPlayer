package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.PlayerController;
import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.AnimationUtils;
import com.eyes3d.eyes3dplayer.utils.FormatUtils;

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
    private TextView mTvCurrentTime, mTvTotalTime;
    private Handler mHandler;
    private PlayerController mPlayerController;
    private UpdateSeekBarRunnable mUpdateSeekBarRunnable;
    private int mPlayViewWidth,
            mPlayViewHeight;

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
        mSeekBar = findViewById(R.id.seekbar_video_bottom_layout_progress_bar);
        mTvCurrentTime = findViewById(R.id.tv_video_bottom_layout_video_current_titme);
        mTvTotalTime = findViewById(R.id.tv_video_bottom_layout_video_total_time);

        mHandler = new Handler(Looper.getMainLooper());
        mUpdateSeekBarRunnable=new UpdateSeekBarRunnable();


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
    }

    void onPlayPause() {
        mPlaying = false;
        mBtnPlayAndPause.setBackgroundResource(R.mipmap.btn_bg_stop);
    }

    /*播放器准备完成*/
    public void onPlayerPrepared(PlayerController controller) {
        mPlayerController=controller;
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
        mTvTotalTime.setText(FormatUtils.mssToString(controller.getDuration()));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvCurrentTime.setText(FormatUtils.mssToString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                controller.seekTo(seekBar.getProgress());
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
            if (EyesVideoBottomLayout.this.getVisibility() == GONE||mPlayerController==null){
                return;
            }
           mSeekBar.setProgress((int) mPlayerController.getCurrentPosition());
            mHandler.postDelayed(this,500);

        }
    }

    @Override
    public void show() {
        super.show();
        startUpdateSeekBarRunnable();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mHandler.removeCallbacks(mUpdateSeekBarRunnable);
    }

    private void startUpdateSeekBarRunnable(){
        mHandler.postDelayed(mUpdateSeekBarRunnable,100);
    }
}
