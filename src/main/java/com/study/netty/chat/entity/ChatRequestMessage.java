package com.study.netty.chat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 17:20
 * @Description
 */
@Data
@NoArgsConstructor
public class ChatRequestMessage extends Message {

    private String to;

    private String content;

    public ChatRequestMessage(String to, String content) {
        this.to = to;
        this.content = content;
    }
}
