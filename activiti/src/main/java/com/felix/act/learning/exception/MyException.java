package com.felix.act.learning.exception;

/**
 * @author : felix.
 * @createTime : 2017/9/21.
 */
public class MyException extends RuntimeException {

    private ErrorCode errorCode;

    public MyException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public MyException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


    public int getCode() {
        return errorCode.getCode();
    }

    public String getMsg() {
        return errorCode.getMsg();
    }

}
