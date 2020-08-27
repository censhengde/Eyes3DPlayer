package com.eyes3d.eyes3dplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Play2DActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private String mPath = " ";
    private EyesPlayer mDPlayer;
    private IPlayerEngine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2d);
        SurfaceView sf = findViewById(R.id.sf);
        sf.getHolder().addCallback(this);
        mDPlayer = EyesPlayer.create2D(this, sf, mPath);
//        mEngine = EyesPlayer.get2DBuilder().setDataSource("").setDisplay(sf.getHolder()).buildEngine();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDPlayer.getEngine().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
