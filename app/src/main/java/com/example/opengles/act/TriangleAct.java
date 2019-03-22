package com.example.opengles.act;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.opengles.views.TriangleSV;

public class TriangleAct extends AppCompatActivity {
    private TriangleSV triangleSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        triangleSV = new TriangleSV(this);
        setContentView(triangleSV);

        triangleSV.requestFocus();
        triangleSV.setFocusableInTouchMode(true); // 设置为可触控
    }

    @Override
    protected void onResume() {
        super.onResume();
        triangleSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        triangleSV.onPause();
    }
}
