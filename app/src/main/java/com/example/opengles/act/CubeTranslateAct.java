package com.example.opengles.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.CubeTranslateSV;

public class CubeTranslateAct extends Activity {
    private CubeTranslateSV cubeTranslateSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        cubeTranslateSV = new CubeTranslateSV(this);
        setContentView(cubeTranslateSV);

        cubeTranslateSV.requestFocus();
        cubeTranslateSV.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cubeTranslateSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cubeTranslateSV.onPause();
    }
}
