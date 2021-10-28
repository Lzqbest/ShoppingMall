package com.study.netty.chat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 15:00
 * @Description
 */
@Data
@NoArgsConstructor
public class LoginRequestMessage extends Message {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    public LoginRequestMessage(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
