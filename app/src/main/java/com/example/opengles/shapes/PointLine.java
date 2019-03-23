package com.example.opengles.shapes;

import android.opengl.GLES30;

import com.blankj.utilcode.util.ResourceUtils;
import com.example.opengles.models.Constant;
import com.example.opengles.utils.MatrixState;
import com.example.opengles.utils.ShaderUtil3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 点跟线
 */
public class PointLine {
    private int mProgram; // 自定义渲染管线着色器程序id
    private int muMVPMatrixHandle; // 总变换矩阵引用
    private int maPositionHandle; // 顶点位置属性引用
    private int maColorHandle; // 顶点颜色属性引用

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;

    private int vCount;

    public PointLine() {
        initVertexData();
        initShader();
    }

    private void initVertexData() {
        vCount = 5;

        float[] vertices = new float[]{
                0, 0, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, 0
        };

        ByteBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = vertexBuffer.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        float colors[] = new float[]{
                1, 1, 0, 0,// 黄
                1, 1, 1, 0,// 白
                0, 1, 0, 0,// 绿
                1, 1, 1, 0,// 白
                1, 1, 0, 0,// 黄
        };

        ByteBuffer colorBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        colorBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = colorBuffer.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }

    private void initShader() {
        String mVertexShader = ResourceUtils.readAssets2String("vertex.sh");
        String mFragmentShader = ResourceUtils.readAssets2String("frag.sh");

        mProgram = ShaderUtil3.createProgram(mVertexShader, mFragmentShader);

        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf() {
        GLES30.glUseProgram(mProgram);
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
                MatrixState.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT,
                false, 3 * 4, mVertexBuffer);
        GLES30.glVertexAttribPointer(maColorHandle, 4, GLES30.GL_FLOAT,
                false, 4 * 4, mColorBuffer);

        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maColorHandle);
        GLES30.glLineWidth(10);

        // 绘制点或者线
        switch (Constant.CURR_DRAW_DOME) {
            case Constant.GL_POINTS:
                GLES30.glDrawArrays(GLES30.GL_POINTS, 0, vCount);
                break;
            case Constant.GL_LINES:
                GLES30.glDrawArrays(GLES30.GL_LINES, 0, vCount);
                break;
            case Constant.GL_LINE_STRIP:
                GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, vCount);
                break;
            case Constant.GL_LINE_LOOP:
                GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, vCount);
                break;
        }
    }
}
