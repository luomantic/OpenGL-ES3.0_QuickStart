package com.example.opengles.shapes;

import android.opengl.GLES30;

import com.blankj.utilcode.util.ResourceUtils;
import com.example.opengles.models.Constant;
import com.example.opengles.utils.MatrixState;
import com.example.opengles.utils.ShaderUtil3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

// 颜色条状物
public class Belt {
    private int mProgram;
    private int muMVPMatrixHandle;
    private int maPositionHandle;
    private int maColorHandle;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;

    private int vCount;

    public Belt() {
        initVertexData();
        intShader();
    }

    private void initVertexData() {
        int n = 6;
        vCount = 2 * (n + 1);

        float angdegBegin = -90;
        float angdegEnd = 90;
        float angdegSpan = (angdegEnd - angdegBegin) / n;

        float[] vertices = new float[vCount * 3];
        int count = 0;

        for (float angdeg = angdegBegin; angdeg <= angdegEnd; angdeg += angdegSpan) {
            double angrad = Math.toRadians(angdeg);//当前弧度
            //当前点
            vertices[count++] = (float) (-0.6f * Constant.UNIT_SIZE * Math.sin(angrad));//顶点x坐标
            vertices[count++] = (float) (0.6f * Constant.UNIT_SIZE * Math.cos(angrad));//顶点y坐标
            vertices[count++] = 0;//顶点z坐标
            //当前点
            vertices[count++] = (float) (-Constant.UNIT_SIZE * Math.sin(angrad));//顶点x坐标
            vertices[count++] = (float) (Constant.UNIT_SIZE * Math.cos(angrad));//顶点y坐标
            vertices[count++] = 0;//顶点z坐标
        }

        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================

        //顶点颜色值数组，每个顶点4个色彩值RGBA
        count = 0;
        float colors[] = new float[vCount * 4];
        for (int i = 0; i < colors.length; i += 8) {
            colors[count++] = 1;
            colors[count++] = 1;
            colors[count++] = 1;
            colors[count++] = 0;

            colors[count++] = 0;
            colors[count++] = 1;
            colors[count++] = 1;
            colors[count++] = 0;
        }

        //创建顶点着色数据缓冲
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mColorBuffer = cbb.asFloatBuffer();//转换为Float型缓冲
        mColorBuffer.put(colors);//向缓冲区中放入顶点着色数据
        mColorBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点着色数据的初始化================end============================
    }

    private void intShader() {
        String mVertexShader = ResourceUtils.readAssets2String("vertex.sh");
        String mFragmentShader = ResourceUtils.readAssets2String("frag.sh");
        mProgram = ShaderUtil3.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用id
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf() {
        GLES30.glUseProgram(mProgram);
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, mVertexBuffer);
        GLES30.glVertexAttribPointer(maColorHandle, 4, GLES30.GL_FLOAT, false, 4 * 4, mColorBuffer);
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maColorHandle);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vCount);
    }
}
