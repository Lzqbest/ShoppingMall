package com.study.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author liaozhiqiang1
 * @date 2021/10/14 15:13
 * @Description
 */
public class SocketChannelTest {

    @Test
    public void client() {
        //创建客户端连接套接字
        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 1000));) {
            socketChannel.configureBlocking(false);
            System.out.println("客户端：连接服务器成功！");
            //构建发送内容缓存对象
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String requestData = "今天天气怎么样？";
            buffer.put(requestData.getBytes());
            buffer.flip();
            //发送
            socketChannel.write(buffer);
            System.out.println("客户端：内容发送成功！|" + requestData);

            ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(responseBuffer);

            System.out.println("客户端：收到服务器响应 | " + new String(responseBuffer.array(), 0, read));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
