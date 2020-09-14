package com.eyes3d.eyes3dplayer.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eyes3d.eyes3dplayer.R;
import com.eyes3d.eyes3dplayer.engine.IjkPlayerEngine;
import com.eyes3d.eyes3dplayer.view.Eyes2DVideoView;

public class TestVedioViewActivity extends AppCompatActivity {
    private Eyes2DVideoView mVideoView;
    private String mPath = "http://eyes3d-v2.oss.eyes3d.com.cn/201358856309964806/video/20200401141608-03071678.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().getDecorView()
        setContentView(R.layout.activity_test_vedio_view);
        mVideoView = findViewById(R.id.eyesVedioView);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mVideoView.addLifecycleOwner(this)
                .setDataSource(mPath)
//                .setPlayerEngine(new IjkPlayerEngine())//设置播放器内核
                .createPlayer();
    }
}