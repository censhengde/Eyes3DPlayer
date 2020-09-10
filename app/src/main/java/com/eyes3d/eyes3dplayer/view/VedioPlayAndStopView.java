package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.ParamsUtils;

/**
 * Shengde·Cen on 2020/9/10
 * 说明：
 */
public class VedioPlayAndStopView extends FloatView {
    private static final String TAG = "PlayAndStopView";
    private Button mBtnPlayStop;
    private boolean isPlay = false;
    private OnClickListener mListener;

    public VedioPlayAndStopView(Context context) {
        super(context);
    }

    public VedioPlayAndStopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setContentView() {
        return R.layout.play_stop_layout;
    }


    @Override
    protected void initView() {
        mBtnPlayStop = findViewById(R.id.btn_play_stop);
        mBtnPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    isPlay = false;
                    stop();
                    if (mListener != null) {
                        mListener.onStop();
                    }
                } else {
                    isPlay = true;
                    play();
                    if (mListener != null) {
                        mListener.onPlay();
                    }
                }
            }
        });
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public void show(boolean isPlaying) {
        super.show();
        if (isPlaying) {
            mBtnPlayStop.setBackgroundResource(R.mipmap.btn_bg_play);
        } else {
            mBtnPlayStop.setBackgroundResource(R.mipmap.btn_bg_stop);
        }
    }


    public void play() {
        isPlay = true;
        if (this.getVisibility() == GONE) {
            this.setVisibility(VISIBLE);
        }
        mBtnPlayStop.setBackgroundResource(R.mipmap.btn_bg_play);

    }

    public void stop() {
        isPlay = false;
        if (this.getVisibility() == GONE) {
            this.setVisibility(VISIBLE);
        }
        mBtnPlayStop.setBackgroundResource(R.mipmap.btn_bg_stop);

    }

    public void onDoubleTap() {
        if (isPlay) {
            isPlay = false;
            stop();
            if (mListener != null) {
                mListener.onStop();
            }
        } else {
            isPlay = true;
            play();
            if (mListener != null) {
                mListener.onPlay();
            }
        }
    }

    public interface OnClickListener {
        void onPlay();

        void onStop();
    }

}
