package com.example.opengles.shapes;

import android.opengl.GLES30;
import android.opengl.Matrix;

import com.blankj.utilcode.util.ResourceUtils;
import com.example.opengles.utils.MatrixState;
import com.example.opengles.utils.ShaderUtil3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class SixPointStar {
    private int mProgram; // 自定义渲染管线着色器程序id
    private int muMVPMatrixHandle;  // 总变换矩阵引用
    private int maPositionHandle;   // 顶点位置属性引用
    private int maColorHandle;      // 顶点颜色属性引用

    /**
     * 具体物体的3D变换矩阵，包括旋转、平移、缩放
     */
    private static float[] mMMatrix = new float[16];

    private FloatBuffer mVertexBuffer;  // 顶点坐标数据缓冲
    private FloatBuffer mColorBuffer;   // 顶点颜色数据缓冲

    private int vCount = 0;

    /**
     * 绕x轴旋转的角度
     */
    public float xAngle = 0;

    /*
     * 绕y轴旋转的角度
     */
    public float yAngle = 0;

    public SixPointStar(float r, float R, float z) {
        initVertexData(R, r, z);
        initShader();
    }

    private void initVertexData(float R, float r, float z) {
        List<Float> fList = new ArrayList<>();
        float tempAngle = 60;
        for (float angle = 0; angle < 360; angle += tempAngle) {
            // 第一个三角形
            // 第一个点的x, y, z坐标
            fList.add(0f);
            fList.add(0f);
            fList.add(z);
            // 第二个点的x, y, z坐标
            float UNIT_SIZE = 1;
            fList.add((float) (R * UNIT_SIZE * Math.cos(Math.toRadians(angle))));
            fList.add((float) (R * UNIT_SIZE * Math.sin(Math.toRadians(angle))));
            fList.add(z);
            // 第三个点的x, y, z坐标
            fList.add((float) (r * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            fList.add((float) (r * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            fList.add(z);

            // 第二个三角形
            // 第一个点的x, y, z坐标
            fList.add(0f);
            fList.add(0f);
            fList.add(z);

            // 第二个点的坐标
            fList.add((float) (r * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            fList.add((float) (r * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            fList.add(z);

            // 第三个点的坐标
            fList.add((float) (R * UNIT_SIZE * Math.cos(angle + tempAngle)));
            fList.add((float) (R * UNIT_SIZE * Math.sin(angle + tempAngle)));
            fList.add(z);
        }

        vCount = fList.size() / 3;
        float[] vertexArray = new float[fList.size()]; // 顶点坐标数组
        for (int i = 0; i < vCount; i++) {
            vertexArray[i * 3] = fList.get(i * 3);
            vertexArray[i * 3 + 1] = fList.get(i * 3 + 1);
            vertexArray[i * 3 + 2] = fList.get(i * 3 + 2);
        }
        ByteBuffer positionBuffer = ByteBuffer.allocateDirect(vertexArray.length * 4);
        positionBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = positionBuffer.asFloatBuffer();
        mVertexBuffer.put(vertexArray);
        mVertexBuffer.position(0);

        // 初始化顶点着色器
        float[] colorArray = new float[vCount * 4]; // 顶点着色器数据的初始化
        for (int i = 0; i < vCount; i++) {
            if (i % 3 == 0) { // 中心点为白色，RGBA 4个通道[1,1,1,0]
                colorArray[i * 4] = 1;
                colorArray[i * 4 + 1] = 1;
                colorArray[i * 4 + 2] = 1;
                colorArray[i * 4 + 3] = 0;
            } else { // 边上的点为淡蓝色，RGBA 4个通道[0.45,0.75,0.75,0]
                colorArray[i * 4] = 0.45f;
                colorArray[i * 4 + 1] = 0.75f;
                colorArray[i * 4 + 2] = 0.75f;
                colorArray[i * 4 + 3] = 0;
            }
        }
        ByteBuffer colorBuffer = ByteBuffer.allocateDirect(colorArray.length * 4);
        colorBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = colorBuffer.asFloatBuffer();
        mColorBuffer.put(colorArray);
        mColorBuffer.position(0);

        //特别提示：由于不同平台,字节顺序不同,数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
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
        GLES30.glUseProgram(mProgram); // 根据着色器程序id, 指定使用某套着色器程序

        // 初始化变换矩阵
        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        // 设置沿z轴正方向位移1
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        // 设置绕y轴旋转yAngle度

        Matrix.rotateM(mMMatrix, 0, yAngle, 0, 1, 0);
        // 设置绕x轴旋转xAngle度

        Matrix.rotateM(mMMatrix, 0, xAngle, 1, 0, 0);
        // 将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0);
        // 将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        mVertexBuffer
                );
        // 将顶点颜色数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        maColorHandle,
                        4,
                        GLES30.GL_FLOAT,
                        false,
                        4 * 4,
                        mColorBuffer
                );
        // 启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        // 启用顶点颜色数据数组
        GLES30.glEnableVertexAttribArray(maColorHandle);
        // 绘制六角星
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
