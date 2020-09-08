package com.eyes3d.eyes3dplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public class BaseVideoView extends RelativeLayout {
    private static final String TAG = "VideoView===========>";
    private SurfaceView mSurfaceView;

    /*音量条*/
    private EyesVolumeBar mVolumeBar;
    /*屏幕亮度条*/
    private EyesScreenBringhtnessBar mBringhtnessBar;
    /*底部操作栏*/
    private EyesVideoBottomLayout mBottomLayout;
    /*顶部操作栏*/
    private EyesVideoTopLayout mTopLayout;
    /*缓冲进度条*/
    private BufferingProgressBar mBufferingProgressBar;


    public BaseVideoView(Context context) {
        this(context, null);
    }

    public BaseVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    private void initSurfaceView(Context context) {
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        mSurfaceView.setLayoutParams(lp);
        this.addView(mSurfaceView);
    }

    private void initSeekBar(Context context) {

    }

    private void initProgressBar() {

    }


    public void pause(){

    }
    public void start(){

    }

}
