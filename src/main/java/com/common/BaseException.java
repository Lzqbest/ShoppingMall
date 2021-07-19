package com.common;

import lombok.Getter;

/**
 * @Author Best-Traveler
 * @Date 2020/11/05
 * @Description 平台自定义异常
 **/
public class BaseException extends RuntimeException {

    @Getter
    private int status = R.HTTP_ERROR;;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(int status,String message){
        super(message);
        this.status = status;
    }

}
