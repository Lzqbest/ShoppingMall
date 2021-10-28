package com.study.netty.chat.entity;


import lombok.NoArgsConstructor;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 16:34
 * @Description
 */
@NoArgsConstructor
public class LoginResponseMessage extends BaseResponseMessage {


    public LoginResponseMessage(boolean isSuccess, String msg) {
        super(isSuccess, msg);
    }
}
