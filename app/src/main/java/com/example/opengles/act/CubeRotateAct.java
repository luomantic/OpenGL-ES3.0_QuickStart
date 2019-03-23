package com.example.opengles.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.CubeRotateSV;

public class CubeRotateAct extends Activity {
    private CubeRotateSV cubeRotateSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        cubeRotateSV = new CubeRotateSV(this);
        setContentView(cubeRotateSV);

        cubeRotateSV.requestFocus();
        cubeRotateSV.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cubeRotateSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cubeRotateSV.onPause();
    }
}
