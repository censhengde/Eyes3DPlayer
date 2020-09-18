package com.eyes3d.eyes3dplayer.test;

import android.os.Bundle;
import android.view.SurfaceView;

import com.eyes3d.eyes3dplayer.BasePlay2DActivity;
import com.eyes3d.eyes3dplayer.EyesPlayer2;
import com.eyes3d.eyes3dplayer.R;

public class TestEyesPlayer2Activity extends BasePlay2DActivity {
    private String mPath = "http://eyes3d-v2.oss.eyes3d.com.cn/201358856309964806/video/20200401141608-03071678.mp4";
    private EyesPlayer2.Eyes2DBuilder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_eyes_player2);
        SurfaceView sfv = findViewById(R.id.sfv);
        mBuilder = EyesPlayer2.create2D()
                .setSurfaceView(sfv)
                .setContext(this)
                .setOwner(this)
                .setDataSource(mPath)
                .setStateObserver(this);
    }

    private boolean mfirst = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mfirst) {
            mfirst = false;
            mBuilder.create();
        }
    }
}
