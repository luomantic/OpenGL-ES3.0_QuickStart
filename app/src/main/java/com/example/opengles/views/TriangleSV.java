package com.example.opengles.views;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;

import com.example.opengles.shapes.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TriangleSV extends GLSurfaceView {

    final float ANGLE_SPAN = 0.375f; // 每次三角形旋转的角度

    private ScreenRenderer screenRenderer; // 自定义渲染器

    public TriangleSV(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        screenRenderer = new ScreenRenderer();
        this.setRenderer(screenRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY); // 设置渲染模式为主动渲染
    }

    public TriangleSV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private class ScreenRenderer implements GLSurfaceView.Renderer {
        private Triangle triangle;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 设置屏幕背景颜色RGBA
            GLES30.glClearColor(0,0,0,1.0f);
            triangle = new Triangle();
            // 打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            RotateThread rotateThread = new RotateThread();
            rotateThread.start();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // 设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);
            // 计算GLSurfaceView的宽高比
            float ratio = (float)width / height;
            // 调用此方法 计算产生透视投影矩阵
            Matrix.frustumM(Triangle.mProjMatrix, 0, -ratio, ratio, -1, 1, 1, 10);
            // 调用此方法 产生摄像机9参数位置矩阵
            Matrix.setLookAtM(Triangle.mVMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            // 清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            // 绘制三角形对象
            triangle.drawSelf();
        }
    }

    public class RotateThread extends Thread {
        private boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                screenRenderer.triangle.xAngle = screenRenderer.triangle.xAngle + ANGLE_SPAN;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
