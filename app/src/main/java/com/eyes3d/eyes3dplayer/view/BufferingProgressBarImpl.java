package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.AppCompatTextView;

import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：这里可以自由定制控件样式、行为。
 */
public class BufferingProgressBarImpl extends BufferingProgressBar {


    private AppCompatTextView mTvNetworkSpeed;
    private ProgressBar mBar;

    public BufferingProgressBarImpl(Context context, @LayoutRes int layoutResID) {
        super(context, layoutResID);
        mTvNetworkSpeed = findViewById(R.id.tv_network_speed);
        mBar = findViewById(R.id.pb_buffering);
    }


    public void setNetworkSpeed(String speed) {
        if (mTvNetworkSpeed == null) return;
        mTvNetworkSpeed.setText(speed);
    }


}
