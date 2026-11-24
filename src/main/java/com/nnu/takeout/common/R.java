package com.nnu.takeout.common;

import java.io.Serializable;

/**
 * 统一响应结果封装
 * code：1 成功，0 失败
 *
 * @param <T> 数据类型
 */
public class R<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public R() {
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 成功（带数据） */
    public static <T> R<T> success(T data) {
        return new R<>(1, "操作成功", data);
    }

    /** 成功（不带数据） */
    public static <T> R<T> success() {
        return new R<>(1, "操作成功", null);
    }

    /** 失败（带提示信息） */
    public static <T> R<T> error(String msg) {
        return new R<>(0, msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
