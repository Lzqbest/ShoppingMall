package com.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Best-Traveler
 * @Date 2020/11/05
 * @Description
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    private static final Long serialVersionUID = 1L;

    public static final int HTTP_OK = 200;

    public static final int HTTP_ERROR = 400;

    public static final String HTTP_OK_MSG = "操作成功！";

    public static final String HTTP_ERROR_MSG = "操作失败！";

    /**
     * 状态码
     */
    private int status;

    /**
     * 提示信息
     */
    private String message;

    private T Data;

    public static R success() {
        return new R(R.HTTP_OK, R.HTTP_OK_MSG);
    }

    public static R success(String msg) {
        return new R(R.HTTP_OK, msg);
    }

    public static R success(Object data) {
        return new R(R.HTTP_OK, R.HTTP_OK_MSG, data);
    }

    public static R fail() {
        return new R(R.HTTP_ERROR, R.HTTP_ERROR_MSG);
    }

    public static R fail(String msg) {
        return new R(R.HTTP_ERROR, msg);
    }

    public static R fail(int status, String msg) {
        return new R(status, msg);
    }

    public R(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
