package com.study.netty.chat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 16:39
 * @Description
 */
@Data
@NoArgsConstructor
public class BaseResponseMessage extends Message {

    private boolean isSuccess;

    private String msg;

    public BaseResponseMessage(boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        this.msg = msg;
    }
}
