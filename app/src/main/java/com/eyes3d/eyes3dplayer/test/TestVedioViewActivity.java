package com.eyes3d.eyes3dplayer.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eyes3d.eyes3dplayer.R;
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
        mVideoView.addLifecycleOwner(this)
                .setDataSource(mPath).createPlayer();
////                .setPlayerEngine(new IjkPlayerEngine())//设置播放器内核
////                .createPlayer();
//        mVideoView.getEngineBuider()
//                .setDataSource(mPath)
//                .setContext(this)
//                .setLifecycleOwner(this)
//                .

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private boolean mfirst=true;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
     if (mfirst){
         mfirst=false;
//         mVideoView.createPlayer();/*这句放到这里调用可以让缓冲条提前显示出来，用户体验更好*/
     }
    }
}