package com.felix.learning.dynamicdatasource.controller.dto;

/**
 * @author : xufei.
 * @createTime : 2017/4/10.
 */
public class RespDTO<T> {


    public int code = 0;
    public String error = "";
    public T data;

    public static RespDTO onSuc(Object data) {
        RespDTO resp = new RespDTO();
        resp.data = data;
        return resp;
    }
}
