package com.example.opengles.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.TriangleStripSV;

public class TriangleStripAct extends Activity {
    private TriangleStripSV stripSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        stripSV = new TriangleStripSV(this);
        setContentView(stripSV);

        stripSV.requestFocus();
        stripSV.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        stripSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stripSV.onPause();
    }
}
