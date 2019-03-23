package com.example.opengles.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.opengles.R;
import com.example.opengles.models.Constant;
import com.example.opengles.views.PointLineSV;

public class PointLineAct extends Activity {
    private PointLineSV pointLineSV;
    private RadioButton rb_point;
    private RadioButton rb_line;
    private RadioButton rb_line_strip;
    private RadioButton rb_line_loop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_point_line);

        pointLineSV = new PointLineSV(this);

        LinearLayout linearLayout = findViewById(R.id.ll_point_line);
        linearLayout.addView(pointLineSV);

        pointLineSV.requestFocus();
        pointLineSV.setFocusableInTouchMode(true);

        initRadioButton();
    }

    private void initRadioButton() {
        rb_point = findViewById(R.id.rb_point);
        rb_point.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Constant.CURR_DRAW_DOME = Constant.GL_POINTS;
                    rb_line.setChecked(false);
                    rb_line_strip.setChecked(false);
                    rb_line_loop.setChecked(false);
                }
            }
        });

        rb_line = findViewById(R.id.rb_line);
        rb_line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Constant.CURR_DRAW_DOME = Constant.GL_LINES;
                    rb_point.setChecked(false);
                    rb_line_strip.setChecked(false);
                    rb_line_loop.setChecked(false);
                }
            }
        });

        rb_line_strip = findViewById(R.id.line_strip);
        rb_line_strip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Constant.CURR_DRAW_DOME = Constant.GL_LINE_STRIP;
                    rb_point.setChecked(false);
                    rb_line.setChecked(false);
                    rb_line_loop.setChecked(false);
                }
            }
        });

        rb_line_loop = findViewById(R.id.line_loop);
        rb_line_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Constant.CURR_DRAW_DOME = Constant.GL_LINE_LOOP;
                    rb_point.setChecked(false);
                    rb_line.setChecked(false);
                    rb_line_strip.setChecked(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pointLineSV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pointLineSV.onPause();
    }
}
