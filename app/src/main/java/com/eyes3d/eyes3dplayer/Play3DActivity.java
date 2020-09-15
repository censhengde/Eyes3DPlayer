package com.eyes3d.eyes3dplayer;

import com.eyes3d.eyes3dplayer.view.Eyes3DVedioView;

public class Play3DActivity extends BasePlay2DActivity {
    private static final String TAG = "Play3DActivity===>";

    String path = "";


    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_main);
        Eyes3DVedioView glsfv = findViewById(R.id.glsfv);
//        mPlayerCtrl = EyesPlayer.create3D(this, this,glsfv,
//                new Eyes3DRendererImpl(glsfv, R.raw.gl_3d_render_vert_shader, R.raw.gl_3d_render_frag_shader),
//                path);
    }

    @Override
    public void onBufferingEnd(PlayerController playerCtrl, long currPosition) {
        super.onBufferingEnd(playerCtrl, currPosition);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
