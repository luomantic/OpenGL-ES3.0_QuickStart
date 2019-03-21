package com.example.opengles.utils;

import android.opengl.Matrix;

public class MatrixState {
    private static float[] mProjMatrix = new float[16]; // 4x4矩阵，投影用
    private static float[] mVMatrix = new float[16]; // 摄像机位置朝向9参数矩阵
    private static float[] mMVPMatrix; // 最终的总变换矩阵

    /**
     * 设置摄像机的方法
     * @param cx 摄像机位置的X、Y、Z坐标
     * @param cy 摄像机位置的X、Y、Z坐标
     * @param cz 摄像机位置的X、Y、Z坐标
     * @param tx 观察目标点X、Y、Z坐标
     * @param ty 观察目标点X、Y、Z坐标
     * @param tz 观察目标点X、Y、Z坐标
     * @param upx up向量在X、Y、Z轴上的分量
     * @param upy up向量在X、Y、Z轴上的分量
     * @param upz up向量在X、Y、Z轴上的分量
     */
    public static void setCamera(
            float cx,
            float cy,
            float cz,
            float tx,
            float ty,
            float tz,
            float upx,
            float upy,
            float upz
    ){
        Matrix.setLookAtM(
                mVMatrix,       // 存储生成矩阵元素的float[]类型数组
                0,     // //填充起始偏移量
                cx, cy, cz,
                tx, ty, tz,
                upx, upy, upz
        );
    }

    /**
     * 设置正交投影的方法
     * @param left      near面的left
     * @param right     near面的right
     * @param bottom    near面的bottom
     * @param top       near面的top
     * @param near      near面的near
     * @param far       near面的far
     */
    public static void setProjectOrtho(
            float left,
            float right,
            float bottom,
            float top,
            float near,
            float far
    ){
        Matrix.orthoM(
                mProjMatrix, // 存储生成矩阵元素的float[]类型数组
                0,  // 填充起始偏移量
                left, right,
                bottom, top,
                near, far
        );
    }

    public static float[] getFinalMatrix(float[] spec) {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }
}
