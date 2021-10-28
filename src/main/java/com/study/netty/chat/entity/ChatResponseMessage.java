package com.study.netty.chat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 17:21
 * @Description
 */
@Data
@NoArgsConstructor
public class ChatResponseMessage extends BaseResponseMessage {

    private String from;

    private String content;

    public ChatResponseMessage(boolean isSuccess, String msg, String from, String content) {
        super(isSuccess, msg);
        this.from = from;
        this.content = content;
    }
    public ChatResponseMessage(boolean isSuccess, String msg) {
        super(isSuccess, msg);
    }
}
