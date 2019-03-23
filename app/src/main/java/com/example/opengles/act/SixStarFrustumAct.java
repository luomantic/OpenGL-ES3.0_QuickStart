package com.example.opengles.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.SixStarFrustumSV;

/**
 * 正交投影
 */
public class SixStarFrustumAct extends Activity {
    private SixStarFrustumSV sixStarFrustumSv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sixStarFrustumSv = new SixStarFrustumSV(this);
        setContentView(sixStarFrustumSv);

        sixStarFrustumSv.requestFocus();
        sixStarFrustumSv.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sixStarFrustumSv.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sixStarFrustumSv.onPause();
    }
}
