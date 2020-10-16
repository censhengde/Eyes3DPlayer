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

    public void setSensitivity(int sensitivity) {
        mSensitivity = sensitivity;
    }

    /*灵敏度*/
    protected int mSensitivity=1;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

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

    abstract void onAdjustGesture(MotionEvent e1, MotionEvent e2, int parentHeight, float distanceY);
}
