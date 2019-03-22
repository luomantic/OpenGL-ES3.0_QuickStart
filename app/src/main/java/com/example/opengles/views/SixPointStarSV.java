package com.example.opengles.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.opengles.shapes.SixPointStar;
import com.example.opengles.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SixPointStarSV extends GLSurfaceView {
    private ScreenRenderer screenRenderer;

    private float mPreviousX; //上次的触控位置X坐标
    private float mPreviousY; //上次的触控位置Y坐标

    public SixPointStarSV(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        screenRenderer = new ScreenRenderer();
        setRenderer(screenRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class ScreenRenderer implements Renderer {
        SixPointStar[] ha = new SixPointStar[6]; // 六角形数组

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 设置屏幕背景色RGBA
            GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);
            // 创建六角星数组中的各个六角星
            for(int i=0;i<ha.length;i++)
            {
                //ha[i]=new SixPointStar(0.2f,0.5f,-0.3f*i); // 正交投影下创建六角形
                ha[i]=new SixPointStar(0.4f,1.0f,-1.0f*i); // 透视投影下创建六角形
            }
            // 打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // 设置视口的大小及位置
            GLES30.glViewport(0, 0, width, height);
            // 计算视口的宽高比
            float ratio= (float) width / height;

            // 设置正交投影
            // MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 10);

            // 设置透视投影
            MatrixState.setProjectFrustum(-ratio*0.4f, ratio*0.4f, -1*0.4f, 1*0.4f, 1, 50);

            // 设置摄像机
            MatrixState.setCamera(
                    0, 0, 3f,
                    0, 0, 0f,
                    0f, 1.0f, 0.0f
            );
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //循环绘制各个六角星
            for(SixPointStar h:ha)
            {
                h.drawSelf();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();//获取此次触控的y坐标
        float x = event.getX();//获取此次触控的x坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE://若为移动动作
                float dy = y - mPreviousY;//计算触控位置的Y位移
                float dx = x - mPreviousX;//计算触控位置的X位移
                for(SixPointStar h:screenRenderer.ha)//设置各个六角星绕x轴、y轴旋转的角度
                {
                    // 角度缩放比例
                    float TOUCH_SCALE_FACTOR = 180.0f / 320;
                    h.yAngle += dx * TOUCH_SCALE_FACTOR;
                    h.xAngle+= dy * TOUCH_SCALE_FACTOR;
                }
        }
        mPreviousY = y;//记录触控笔y坐标
        mPreviousX = x;//记录触控笔x坐标
        return true;
    }
}
