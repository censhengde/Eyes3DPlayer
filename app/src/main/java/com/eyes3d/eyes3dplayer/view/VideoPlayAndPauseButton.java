package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.utils.EyesLog;
import com.eyes3d.eyes3dplayer.utils.ParamsUtils;

/**
 * Shengde·Cen on 2020/9/10
 * 说明：
 */
public class VideoPlayAndPauseButton extends FloatView {
    private static final String TAG = "PlayAndStopView";
    private Button mBtnPlayStop;
    private boolean mIsPlaying = false;
    private OnPlayAndPauseListener mListener;

    public VideoPlayAndPauseButton(Context context) {
        super(context);
    }

    public VideoPlayAndPauseButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int retRootLayout() {
        return R.layout.play_stop_layout;
    }


    @Override
    protected void initView() {
        mBtnPlayStop = findViewById(R.id.btn_play_stop);
        mBtnPlayStop.setOnClickListener(v -> {
            if (mIsPlaying) {
                mIsPlaying = false;
                pause();
                if (mListener != null) {
                    mListener.onPause();
                }
            } else {
                mIsPlaying = true;
                play();
                if (mListener != null) {
                    mListener.onPlay();
                }
            }
        });
    }

    public void setOnPlayAndPauseListener(OnPlayAndPauseListener listener) {
        ParamsUtils.checkNotNull(listener,"OnPlayAndPauseListener 为null");
        mListener = listener;
    }


    public void play() {
        mIsPlaying = true;
        super.show();
        mBtnPlayStop.setBackgroundResource(R.mipmap.btn_bg_play);


    }

    public void pause() {
        mIsPlaying = false;
        super.show();
        mBtnPlayStop.setBackgroundResource(R.mipmap.btn_bg_stop);
    }


    public interface OnPlayAndPauseListener {
        void onPlay();

        void onPause();
    }

    /*避免频繁点击造成消息重复*/
    private int autoDismissMessage = 0;

    public void autoDismiss(int delayMillis) {
        autoDismissMessage++;
        postDelayed(() -> {
                    EyesLog.e(this, "autoDismissMessage=" + autoDismissMessage);
                    if (autoDismissMessage == 1 && mIsPlaying) {//多次点击只有最后一次能进来
                        dismiss();
                    }
                    autoDismissMessage--;
                }
                , delayMillis);
    }
}
