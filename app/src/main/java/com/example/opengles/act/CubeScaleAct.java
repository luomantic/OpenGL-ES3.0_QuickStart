package com.example.opengles.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.CubeScaleSV;

public class CubeScaleAct extends Activity {
    private CubeScaleSV cubeScaleSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        cubeScaleSV = new CubeScaleSV(this);
        setContentView(cubeScaleSV);

        cubeScaleSV.requestFocus();
        cubeScaleSV.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cubeScaleSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cubeScaleSV.onPause();
    }
}
