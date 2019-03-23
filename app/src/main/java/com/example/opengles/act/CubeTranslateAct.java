package com.example.opengles.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.CubeSV;

public class CubeTranslateAct extends Activity {
    private CubeSV cubeSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        cubeSV = new CubeSV(this);
        setContentView(cubeSV);

        cubeSV.requestFocus();
        cubeSV.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cubeSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cubeSV.onPause();
    }
}
