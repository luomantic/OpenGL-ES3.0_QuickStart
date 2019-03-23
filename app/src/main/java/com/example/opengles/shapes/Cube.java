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
 * 立方体
 */
public class Cube {
    private int mProgram; // 自定义渲染管线着色器程序id
    private int muMVPMatrixHandle;  // 总变换矩阵引用
    private int maPositionHandle;   // 顶点位置属性引用
    private int maColorHandle;      // 顶点颜色属性引用

    private FloatBuffer mVertexBuffer; // 顶点坐标数据缓冲
    private FloatBuffer mColorBuffer;  // 顶点颜色数据缓冲

    private int vCount = 0;

    public Cube() {
        initVertexData();
        initShader();
    }

    private void initVertexData() {
        vCount = 12 * 6;
        float vertices[] = new float[]{
                //前面
                0, 0, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                0, 0, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                0, 0, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                0, 0, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //后面
                0, 0, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, 0, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, 0, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, 0, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                //左面
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //右面
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //上面
                0, Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                0, Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //下面
                0, -Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                0, -Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, -Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, -Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
        };

        // 创建顶点坐标数据缓冲
        ByteBuffer positionBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        positionBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = positionBuffer.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        // 顶点颜色值数组，每个顶点4个色彩值RGBA
        float colors[]=new float[]{
                //前面
                1,1,1,0,//中间为白色
                1,0,0,0,
                1,0,0,0,
                1,1,1,0,//中间为白色
                1,0,0,0,
                1,0,0,0,
                1,1,1,0,//中间为白色
                1,0,0,0,
                1,0,0,0,
                1,1,1,0,//中间为白色
                1,0,0,0,
                1,0,0,0,
                //后面
                1,1,1,0,//中间为白色
                0,0,1,0,
                0,0,1,0,
                1,1,1,0,//中间为白色
                0,0,1,0,
                0,0,1,0,
                1,1,1,0,//中间为白色
                0,0,1,0,
                0,0,1,0,
                1,1,1,0,//中间为白色
                0,0,1,0,
                0,0,1,0,
                //左面
                1,1,1,0,//中间为白色
                1,0,1,0,
                1,0,1,0,
                1,1,1,0,//中间为白色
                1,0,1,0,
                1,0,1,0,
                1,1,1,0,//中间为白色
                1,0,1,0,
                1,0,1,0,
                1,1,1,0,//中间为白色
                1,0,1,0,
                1,0,1,0,
                //右面
                1,1,1,0,//中间为白色
                1,1,0,0,
                1,1,0,0,
                1,1,1,0,//中间为白色
                1,1,0,0,
                1,1,0,0,
                1,1,1,0,//中间为白色
                1,1,0,0,
                1,1,0,0,
                1,1,1,0,//中间为白色
                1,1,0,0,
                1,1,0,0,
                //上面
                1,1,1,0,//中间为白色
                0,1,0,0,
                0,1,0,0,
                1,1,1,0,//中间为白色
                0,1,0,0,
                0,1,0,0,
                1,1,1,0,//中间为白色
                0,1,0,0,
                0,1,0,0,
                1,1,1,0,//中间为白色
                0,1,0,0,
                0,1,0,0,
                //下面
                1,1,1,0,//中间为白色
                0,1,1,0,
                0,1,1,0,
                1,1,1,0,//中间为白色
                0,1,1,0,
                0,1,1,0,
                1,1,1,0,//中间为白色
                0,1,1,0,
                0,1,1,0,
                1,1,1,0,//中间为白色
                0,1,1,0,
                0,1,1,0,
        };

        ByteBuffer colorBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        colorBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = colorBuffer.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }

    private void initShader() {
        // 顶点着色器代码脚本
        String mVertexShader = ResourceUtils.readAssets2String("vertex.sh");
        // 片元着色器代码脚本
        String mFragmentShader = ResourceUtils.readAssets2String("frag.sh");

        mProgram = ShaderUtil3.createProgram(mVertexShader, mFragmentShader);
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf() {
        GLES30.glUseProgram(mProgram);
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);

        //为画笔指定顶点位置数据
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //为画笔指定顶点着色数据
        GLES30.glVertexAttribPointer
                (
                        maColorHandle,
                        4,
                        GLES30.GL_FLOAT,
                        false,
                        4*4,
                        mColorBuffer
                );
        //允许顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maColorHandle);
        //绘制立方体
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0, vCount);
    }
}
