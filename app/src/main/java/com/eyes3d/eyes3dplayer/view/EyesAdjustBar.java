package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.DimenUtils;

/**
 * Shengde·Cen on 2020/10/16
 * 说明：
 */
abstract class EyesAdjustBar extends LinearLayoutCompat implements OnSeekBarChangeListener {
    /*图标*/
    protected ImageView mIcon;

    /*显示条*/
    protected SeekBar mSeekBar;

    /*将系统默认音量调节范围放大到0--100*/
    protected int mMaxSeekBarValue = 100;

    /*进度增量*/
    protected int mDeltaProgress=0;
    protected int mOldProgress=0;

    public void setSensitivity(int sensitivity) {
        mSensitivity = sensitivity;
    }

    /*灵敏度*/
    protected int mSensitivity = 5;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mDeltaProgress=progress-mOldProgress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
              mOldProgress=seekBar.getProgress();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public EyesAdjustBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(HORIZONTAL);
        super.setGravity(Gravity.CENTER_VERTICAL);
        mIcon = new ImageView(context);
        mSeekBar = new SeekBar(context);
        final LinearLayout.LayoutParams ivIconLp = new LinearLayout.LayoutParams(context, attrs);
        final LinearLayout.LayoutParams seekBarLp = new LinearLayout.LayoutParams(context, attrs);

        ivIconLp.width = DimenUtils.getDimension(context, R.dimen.dp_32);
        ivIconLp.height = DimenUtils.getDimension(context, R.dimen.dp_32);
        mIcon.setLayoutParams(ivIconLp);

        seekBarLp.width = DimenUtils.getDimension(context, R.dimen.dp_170);
        seekBarLp.height = DimenUtils.getDimension(context, R.dimen.dp_5);
        mSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbarbg, null));
        mSeekBar.setThumb(null);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setLayoutParams(seekBarLp);
        super.addView(mIcon);
        super.addView(mSeekBar);
    }

    void onAdjustGesture(MotionEvent e1, MotionEvent e2, int parentHeight, float distanceY) {
        final SeekBar seekBar = mSeekBar;
        /*将整个播放界面高度MAX_SEEK_BAR_VALUE等分*/
        final float scale = distanceY / (float) parentHeight;
        final float offsetProgress = scale * mMaxSeekBarValue * mSensitivity;
        int newProgress = (int) (seekBar.getProgress() + offsetProgress);
        if (newProgress < 0) {
            newProgress = 0;
        }
        if (newProgress > mMaxSeekBarValue) {
            newProgress = mMaxSeekBarValue;
        }
        seekBar.setProgress(newProgress);
    }
}
