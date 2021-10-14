package com.study.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author liaozhiqiang1
 * @date 2021/10/9 17:37
 * @Description
 */
public class ServerSocketChannelTest {

    @Test
    public void server() {
        //创建服务器套接字通道
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();) {
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 1000));
            System.out.println("服务器：服务器绑定成功！");
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            //将选择器注册到服务socket   SelectionKey.OP_ACCEPT接收就绪
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //注册在选择器中的服务通道数量
            //selector.keys();
            //等待客户端连接  暂无客户端连接时阻塞
            selector.select();
            //select选择器等待IO操作的通道
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    System.out.println("服务器：收到客户端请求！");
                    // 获取来自客户端的连接，创建 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 把该 Channel 注册到 selector 上（也就是理论上的把多个客户端的channel注册到selector上，用一个线程轮询）
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = channel.read(buffer);
                    System.out.println("服务器：收到客户端内容 | " + new String(buffer.array(), 0, read));

                    ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
                    String responseData = "今天天气非常好，可以晒太阳。";
                    responseBuffer.put(responseData.getBytes());
                    responseBuffer.flip();
                    channel.write(responseBuffer);
                    System.out.println("服务器：回复客户端内容 | " + responseData);
                }

                //处理完删除
                iterator.remove();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
