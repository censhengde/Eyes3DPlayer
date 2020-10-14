package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/4
 * 说明：
 */
public class EyesVolumeBar extends LinearLayoutCompat {
    /*小喇叭*/
    private ImageView mIcon;
    /*音量条*/
    private SeekBar mSeekBar;

    public EyesVolumeBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(HORIZONTAL);
        mIcon = new ImageView(context);
        mSeekBar = new SeekBar(context);
        final LinearLayout.LayoutParams ivHornLp = new LinearLayout.LayoutParams(context,attrs);
        final LinearLayout.LayoutParams seekBarLp =new LinearLayout.LayoutParams(context,attrs);

        ivHornLp.width = (int) getResources().getDimension(R.dimen.dp_32);
        ivHornLp.height = (int) getResources().getDimension(R.dimen.dp_32);
        ivHornLp.gravity = Gravity.CENTER_VERTICAL;
        mIcon.setBackgroundResource(retIconID());

        seekBarLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        seekBarLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbarbg, null));
        mSeekBar.setThumb(getResources().getDrawable(R.drawable.seekbarthum110, null));
        mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSeekBar.setMax(100);
        super.addView(mIcon);
        super.addView(mSeekBar);

    }

    protected @DrawableRes
    int retIconID() {
        return R.mipmap.ic_horn;
    }

    private final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    /*调节手势*/
    void onAdjustGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){

    }

}
