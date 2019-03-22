package com.example.opengles.utils;

import android.opengl.GLES30;
import android.util.Log;

/**
 * openGL ES3.0 - 加载顶点Shader与片元Shader的工具类
 */
public class ShaderUtil3 {

    private static final String TAG = "ES30_ERROR";

    /**
     * 创建shader程序的方法
     * @param vertexSource 顶点shader的脚本字符串
     * @param fragmentSource 片元shader的脚本字符串
     * @return GLES30.glCreateProgram()创建的shader程序, 若链接失败，则返回program = 0;
     */
    public static int createProgram(String vertexSource, String fragmentSource){
        // 加载顶点着色器
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        // 加载片元着色器
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragmentShader == 0) {
            return 0;
        }

        // 创建 shader程序
        int program = GLES30.glCreateProgram();
        // 若程序创建成功，则向程序中加入顶点着色器于片元着色器
        if (program != 0) {
            // 向程序中加入顶点着色器
            GLES30.glAttachShader(program, vertexShader);
            checkGLError();
            // 向程序中加入片元着色器
            GLES30.glAttachShader(program, fragmentShader);
            checkGLError();
            // 链接程序
            GLES30.glLinkProgram(program);
            // 存放链接成功program数量的数组
            int[] linkStatus = new int[1];
            // 获取program的链接情况
            GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0);
            // 若链接失败则报错并删除程序
            if (linkStatus[0] != GLES30.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES30.glGetProgramInfoLog(program));
                GLES30.glDeleteProgram(program);
                program = 0;
            }
        }

        return program;
    }

    /**
     * 加载指定 ‘shader脚本字符串’ 的方法
     * @param shaderType shader的类型  GLES30.GL_VERTEX_SHADER(顶点)   GLES30.GL_FRAGMENT_SHADER(片元)
     * @param source shader的脚本字符串
     * @return 返回shader，若shader语法错误编译不通，则返回shader = 0;
     */
    private static int loadShader(int shaderType, String source){
        // 创建一个 shader，并记录其id
        int shader = GLES30.glCreateShader(shaderType);

        // 若创建成功则加载shader
        if (shader != 0) {
            // 加载shader的源代码
            GLES30.glShaderSource(shader, source);
            // 编译shader
            GLES30.glCompileShader(shader);
            // 存放编译成功shader数量的数组
            int[] compiled = new int[1];
            // 获取shader的编译情况
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);

            if (compiled[0] == 0) { // 若编译失败，则显示错误日志，并删除此shader
                Log.e(TAG, "Could not compile shader" + shaderType + ":");
                Log.e(TAG, GLES30.glGetShaderInfoLog(shader));
                GLES30.glDeleteShader(shader);
                shader = 0;
            }
        }

        return shader;
    }


    /**
     * 检查向GPU着色程序中，加入 ‘顶点着色器脚本’ 和 ‘片元着色器脚本’ 是否加载失败
     */
    private static void checkGLError() {
        int error;
        if ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            Log.e(TAG, "glAttachShader" + ": glError" + error);
            throw new RuntimeException("glAttachShader" + ": glError" + error);
        }
    }

}
