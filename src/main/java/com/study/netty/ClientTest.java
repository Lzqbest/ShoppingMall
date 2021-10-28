package com.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;


/**
 * @author liaozhiqiang1
 * @date 2021/10/18 15:46
 * @Description
 */
@Slf4j
public class ClientTest {

    public static void main(String[] args) throws InterruptedException {
        //启动器
        ChannelFuture channelFuture = new Bootstrap()
                //eventLoop
                .group(new NioEventLoopGroup())
                //选择客户端Channel实现
                .channel(NioSocketChannel.class)
                //添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = ((ByteBuf) msg);
                                log.info("收到服务器消息： 【{}】", buf.toString(Charset.defaultCharset()));
                                //回收ByteBuf
                                buf.release();
                            }
                        });
                    }
                })
                //连接服务器
                .connect(new InetSocketAddress("localhost", 9000));
        //阻塞方法，直到连接建立
        channelFuture.sync();
        //连接对象
        Channel channel = channelFuture.channel();
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                //向服务器发送数据
                channel.writeAndFlush(scanner.next());
            }
        }).start();

    }
}
