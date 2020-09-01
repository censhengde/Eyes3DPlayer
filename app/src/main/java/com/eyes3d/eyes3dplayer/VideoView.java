package com.eyes3d.eyes3dplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Shengde·Cen on 2020/9/1
 * 说明：
 */
public class VideoView extends RelativeLayout implements PlayerController{
    private static final String TAG = "VideoView===========>";

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    private void initSurfaceView(Context context) {
        SurfaceView sf = new SurfaceView(context);
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        sf.setLayoutParams(lp);
        this.addView(sf);
    }

    private void initSeekBar(Context context) {

    }

    private void initProgressBar() {

    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {

    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void seekTo(int msec) {

    }
}
