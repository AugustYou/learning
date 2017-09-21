package com.felix.act.learning.exception;

/**
 * @author : felix.
 * @createTime : 2017/9/21.
 */
public enum ErrorCode {

    OK(0, ""),
    FAIL(500, "服务器错误"),
    ERROR_ARGS(10001, "参数错误"),
    MODEL_NOT_EXIST(10002, "模型不存在");

    private int code;
    private String msg;


    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ErrorCode codeOf(int code) {
        for (ErrorCode state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        return null;
    }
}
