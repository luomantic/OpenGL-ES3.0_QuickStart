package com.example.opengles.shapes;

import android.opengl.GLES30;
import android.opengl.Matrix;

import com.blankj.utilcode.util.ResourceUtils;
import com.example.opengles.utils.ShaderUtil3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 三角形
 */
public class Triangle {
    /**
     * 4x4 投影矩阵  ( 投影 —— projection, projective )
     */
    public static float[] mProjMatrix = new float[16];

    /**
     * 摄像机位置朝向的参数矩阵  ( 摄像机, 视像管, 光导摄像管 —— vidicon )
     */
    public static float[] mVMatrix = new float[16];

    /**
     * 自定义渲染管线程序id
     */
    private int mProgram;



    /**
     * 总变换矩阵引用
     */
    private int muMVPMatrixHandle;

    /**
     * 顶点位置属性引用
     */
    private int maPositionHandle;

    /**
     * 顶点颜色属性引用
     */
    private int maColorHandle;



    /**
     *  具体物体的移动旋转矩阵，包括旋转、平移、缩放
     */
    private static float[] mMMatrix = new float[16];

    /**
     * 顶点 坐标数据缓冲
     */
    private FloatBuffer mVertexBuffer;
    /**
     * 顶点 着色数据缓冲
     */
    private FloatBuffer   mColorBuffer;

    private int vCount=0;

    /**
     * 绕x轴旋转的角度
     */
    public float xAngle=0;

    public Triangle() {
        initVertexData();
        initShader();
    }

    /**
     * 初始化顶点数据 —— 将数据放入ByteBuffer缓冲中
     */
    private void initVertexData() {
        vCount = 3; // 顶点数量
        final float UNIT_SIZE = 0.2f; // 设置单位长度

        // 顶点 坐标数组
        float vertices[] = new float[] {
                -4*UNIT_SIZE, 0, 0,
                0, -4*UNIT_SIZE, 0,
                4*UNIT_SIZE, 0, 0,
        };

        ByteBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexBuffer.order(ByteOrder.nativeOrder()); // 设置字节顺序为本地操作系统顺序
        mVertexBuffer = vertexBuffer.asFloatBuffer(); // 转换为浮点（float）型缓冲
        mVertexBuffer.put(vertices); // 在缓冲区内写入数据
        mVertexBuffer.position(0); // 设置缓冲区的起始位置

        // 顶点 颜色数据
        float colors[] = new float[] {
                1,1,1,0, // 白色
                0,0,1,0, // 蓝
                0,1,0,0, // 绿
        };

        ByteBuffer colorBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        colorBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = colorBuffer.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }

    /**
     * 初始化着色器 - 创建着色器程序，获取着色器程序的id，并将缓冲好的数据，压入渲染管线
     */
    private void initShader() {
        // 加载 顶点着色器 脚本
        String mVertexShader = ResourceUtils.readAssets2String("vertex.sh");

        // 加载 片元着色器 脚本
        String mfragmentShader = ResourceUtils.readAssets2String("frag.sh");
        // 基于顶点着色器与片元着色器创建程序
        mProgram= ShaderUtil3.createProgram(mVertexShader, mfragmentShader);

        // 获取程序中顶点 位置属性 引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        // 获取程序中顶点 颜色属性 引用
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        // 获取程序中总变换矩阵引用
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf(){
        // 指定使用某套shader程序
        GLES30.glUseProgram(mProgram);
        // 初始化变换矩阵
        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        // 设置沿z轴正方向位置1
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        // 设置绕x轴选择
        Matrix.rotateM(mMMatrix, 0, xAngle, 1,0, 0);
        // 将变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, Triangle.getFinalMatrix(mMMatrix), 0);
        // 将顶点位置数据传送进渲染管线
        GLES30.glVertexAttribPointer(
                maPositionHandle,
                3,
                GLES30.GL_FLOAT,
                false,
                3*4,
                mVertexBuffer
        );
        // 将顶点颜色数据传送进渲染管线
        GLES30.glVertexAttribPointer(
                maColorHandle,
                4,
                GLES30.GL_FLOAT,
                false,
                4*4,
                mColorBuffer
        );
        GLES30.glEnableVertexAttribArray(maPositionHandle); // 启动顶点位置数据
        GLES30.glEnableVertexAttribArray(maColorHandle); // 启动顶点着色数据
        // 绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }

    private static float[] getFinalMatrix(float[] spec) {
        // 最后起作用的总变换矩阵
        float[] mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix,0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }
}
