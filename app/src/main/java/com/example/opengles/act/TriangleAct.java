package com.example.opengles.act;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.opengles.views.LuoGLSurfaceView;

public class TriangleAct extends AppCompatActivity {
    private LuoGLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        surfaceView = new LuoGLSurfaceView(this);
        surfaceView.requestFocus();
        surfaceView.setFocusableInTouchMode(true); // 设置为可触控

        setContentView(surfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }
}
