package com.study.netty.chat.common.protocol;

import com.study.netty.chat.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 9:27
 * @Description 自定义编码解码器  可自定义传输协议
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {

//    自定义协议要数：
//    1.魔数：用来判断是否为无效数据包
//    2.版本号：用于协议升级
//    3.序列号算法：消息正文采用什么序列化和反序列化算法。如：Json、hessian、jdk等
//    4.指令类型：功能类型
//    5.请求序号：双工通信还是异步通信
//    6.正文长度
//    7.正文
    /**
     * 序列化算法 1:JDK序列化  2：Json序列化
     */
    private static final int ALGORITHM = 1;

    private static final Map<Integer, Serializer.Algorithm> ALGORITHM_MAP = new HashMap<>();

    static {
        ALGORITHM_MAP.put(1, Serializer.Algorithm.JDK);
        ALGORITHM_MAP.put(2, Serializer.Algorithm.JSON);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //魔数 7个字节
        out.writeBytes("http://".getBytes(StandardCharsets.UTF_8));
        //版本号：用于版本升级  3个字节
        out.writeBytes("1.0".getBytes(StandardCharsets.UTF_8));
        //序列号算法 1个字节
        out.writeByte(ALGORITHM);
        //指令类型 1个字节
        //out.writeByte(msg.getMessageType());
        //请求序号 1个字节
        //out.writeByte(msg.getSequenceId());
        //不使用指令类型和请求序号 用1个占位符代替
        out.writeByte(0xFF);
        byte[] body = ALGORITHM_MAP.get(ALGORITHM).serialze(msg);
        //正文长度
        out.writeInt(body.length);
        //正文
        out.writeBytes(body);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //魔数 7个字节
        byte[] magicByte = new byte[7];
        in.readBytes(magicByte, 0, 7);
        String magicNum = new String(magicByte, StandardCharsets.UTF_8);
        //版本号：用于版本升级  3个字节
        byte[] versionByte = new byte[3];
        in.readBytes(versionByte, 0, 3);
        String version = new String(versionByte, StandardCharsets.UTF_8);
        //序列化算法 1个字节
        int algorithm = in.readByte();
        //指令类型 1个字节
        //int messageType = in.readByte();
        //请求序号 1个字节
        //int sequenceId = in.readByte();
        //读取1个占位符
        in.readByte();
        //正文长度
        int length = in.readInt();
        //正文
        byte[] bodyByte = new byte[length];
        in.readBytes(bodyByte, 0, length);

        Message message = ALGORITHM_MAP.get(algorithm).deserialze(Message.class, bodyByte);

        out.add(message);
    }
}
