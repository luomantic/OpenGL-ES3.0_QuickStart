package com.example.opengles.views;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.example.opengles.models.Constant;
import com.example.opengles.shapes.Belt;
import com.example.opengles.shapes.Circle;
import com.example.opengles.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TriangleStripSV extends GLSurfaceView {

    public TriangleStripSV(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        ScreenRenderer screenRenderer = new ScreenRenderer();
        setRenderer(screenRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class ScreenRenderer implements GLSurfaceView.Renderer {
        Belt belt; // 条状物
        Circle circle; // 圆

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            circle = new Circle();
            belt = new Belt();
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            GLES30.glEnable(GLES30.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            Constant.ratio = (float) width / height;
            MatrixState.setProjectFrustum(
                    -Constant.ratio, Constant.ratio,
                    -1, 1, 20, 100
            );
            MatrixState.setCamera(
                    0f, 8f, 30f,
                    0f, 0f, 0f,
                    0f, 1.0f, 0f
            );
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            MatrixState.pushMatrix();
            MatrixState.pushMatrix();
            belt.drawSelf();
            MatrixState.popMatrix();
            MatrixState.pushMatrix();
            MatrixState.translate(1.3f, 0, 0);
            circle.drawSelf();
            MatrixState.popMatrix();
            MatrixState.popMatrix();
        }
    }
}
