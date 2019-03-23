package com.example.opengles.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.SixStarOrthoSV;

/**
 * 正交投影
 */
public class SixStarOrthoAct extends Activity {
    private SixStarOrthoSV starOrthoSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        starOrthoSV = new SixStarOrthoSV(this);
        setContentView(starOrthoSV);

        starOrthoSV.requestFocus();
        starOrthoSV.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        starOrthoSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        starOrthoSV.onPause();
    }
}
