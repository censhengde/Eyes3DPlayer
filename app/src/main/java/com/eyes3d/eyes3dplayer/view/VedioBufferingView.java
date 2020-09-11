package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.eyes3d.eyes3dplayer.R;

/**
 * Shengde·Cen on 2020/9/8
 * 说明：这里可以自由定制控件样式、行为。
 */
public class VedioBufferingView extends BufferingProgressBar {


    private AppCompatTextView mTvNetworkSpeed;
    private ProgressBar mBar;

    public VedioBufferingView(Context context) {
        super(context);
    }

    public VedioBufferingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setNetworkSpeed(String speed) {
        if (mTvNetworkSpeed == null) return;
        mTvNetworkSpeed.setText(speed);
    }


    @Override
    protected int retRootLayout() {
        return R.layout.buffering_progress_bar_layout;
    }

    @Override
    protected void initView() {
        mTvNetworkSpeed = findViewById(R.id.tv_network_speed);
        mBar = findViewById(R.id.pb_buffering);
    }
}
