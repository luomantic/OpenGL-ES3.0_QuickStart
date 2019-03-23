package com.example.opengles.utils;

import android.opengl.Matrix;

public class MatrixState {
    private static float[] mProjMatrix = new float[16]; // 4x4矩阵，投影用
    private static float[] mVMatrix = new float[16]; // 摄像机位置朝向9参数矩阵
    private static float[] mMVPMatrix; // 最终的总变换矩阵

    /**
     * 设置摄像机的方法
     *
     * @param cx  摄像机位置的X、Y、Z坐标
     * @param cy  摄像机位置的X、Y、Z坐标
     * @param cz  摄像机位置的X、Y、Z坐标
     * @param tx  观察目标点X、Y、Z坐标
     * @param ty  观察目标点X、Y、Z坐标
     * @param tz  观察目标点X、Y、Z坐标
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
    ) {
        Matrix.setLookAtM(
                mVMatrix,       // 存储生成矩阵元素的float[]类型数组
                0,     // 填充起始偏移量
                cx, cy, cz,
                tx, ty, tz,
                upx, upy, upz
        );
    }

    /**
     * 设置正交投影的方法
     *
     * @param left   near面的left
     * @param right  near面的right
     * @param bottom near面的bottom
     * @param top    near面的top
     * @param near   near面的near
     * @param far    near面的far
     */
    public static void setProjectOrtho(
            float left,
            float right,
            float bottom,
            float top,
            float near,
            float far
    ) {
        Matrix.orthoM(
                mProjMatrix, // 存储生成矩阵元素的float[]类型数组
                0,  // 填充起始偏移量
                left, right,
                bottom, top,
                near, far
        );
    }

    /**
     * 设置透视投影的方法
     *
     * @param left   near面的left
     * @param right  near面的right
     * @param bottom near面的bottom
     * @param top    near面的top
     * @param near   near面的near
     * @param far    near面的far
     */
    public static void setProjectFrustum(
            float left,
            float right,
            float bottom,
            float top,
            float near,
            float far
    ) {
        Matrix.frustumM(
                mProjMatrix, // 存储生成矩阵元素的float[]类型数组
                0,
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

    //////////////////////////// CUBE ////////////////////////////

    private static float[] currMatrix; // 当前变换矩阵

    private static float[][] mStack = new float[10][16]; // 用于保存变换矩阵的栈
    private static int stackTop = -1; // 栈顶索引

    // 产生无任何变换的初始矩阵
    public static void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    // 将当前变换矩阵存入栈中
    public static void pushMatrix() {
        stackTop++; // 栈顶索引加1
        // 当前变换矩阵中的各元素入栈
        System.arraycopy(currMatrix, 0, mStack[stackTop], 0, 16);
    }

    // 从栈顶取出变换矩阵
    public static void popMatrix() {
        // 栈顶矩阵元素进当前变换矩阵
        System.arraycopy(mStack[stackTop], 0, currMatrix, 0, 16);
        stackTop--; // 栈顶索引减1
    }

    // 获取具体物体的总变换矩阵
    public static float[] getFinalMatrix()//计算产生总变换矩阵的方法
    {
        // 摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mTotalMatrix, 0, mVMatrix, 0, currMatrix, 0);
        // 投影矩阵乘以上一步的结果矩阵
        Matrix.multiplyMM(mTotalMatrix, 0, mProjMatrix, 0, mTotalMatrix, 0);
        return mTotalMatrix;
    }

    private static float[] mTotalMatrix = new float[16];//总变换矩阵

    // 沿X、Y、Z轴方向进行平移变换的方法
    public static void translate(float x, float y, float z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    // 旋转
    public static void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    // 缩放
    public static void scale(float x, float y, float z) {
        Matrix.scaleM(currMatrix, 0, x, y, z);
    }

    // 获取具体物体的变换矩阵
    public static float[] getMMatrix() {
        return currMatrix;
    }
}
