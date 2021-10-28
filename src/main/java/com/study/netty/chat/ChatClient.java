package com.study.netty.chat;

import com.study.netty.chat.common.protocol.MessageCodec;
import com.study.netty.chat.entity.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 9:23
 * @Description
 */
@Slf4j
public class ChatClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean status = new AtomicBoolean(false);

        try {
            new Bootstrap()
                    .group(group)
                    //设置5S后连接超时
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(10, 6, 0));
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    if (evt instanceof IdleStateEvent) {
                                        IdleStateEvent e = (IdleStateEvent) evt;
                                        //写空闲事件
                                        if (e.state() == IdleState.WRITER_IDLE) {
                                            log.debug("发送心跳包！");
                                            ctx.channel().writeAndFlush(new PingRequestMessage());
                                        } else if (e.state() == IdleState.READER_IDLE) {
                                            log.debug("未收到服务器响应！");
                                            ctx.channel().close();
                                        }
                                    }
                                    super.userEventTriggered(ctx, evt);
                                }
                            });
                            //根据消息中长度字段的值动态拆分接收的字节缓冲区  解决粘包半包问题
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                            ch.pipeline().addLast(new MessageCodec());
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<PongResponseMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, PongResponseMessage msg) throws Exception {
                                    log.debug("收到服务器心跳响应！");
                                }
                            });
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginResponseMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, LoginResponseMessage msg) throws Exception {
                                    log.info("{}", msg);
                                    if (msg.isSuccess()) {
                                        status.set(true);
                                    }
                                    latch.countDown();
                                }
                            });
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ChatResponseMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
                                    log.info("{}", msg);
                                }
                            });
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    new Thread(() -> {
                                        Scanner scanner = new Scanner(System.in);
                                        System.out.println("请输入用户名：");
                                        String userName = scanner.nextLine();
                                        System.out.println("请输入密码：");
                                        String password = scanner.nextLine();
                                        ctx.channel().writeAndFlush(new LoginRequestMessage(userName, password));

                                        try {
                                            latch.await();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        if (!status.get()) {
                                            ctx.channel().close();
                                            return;
                                        }
                                        while (true) {
                                            System.out.println("===================================");
                                            System.out.println("send [username] [content]");
                                            System.out.println("===================================");
                                            String command = scanner.nextLine();
                                            String[] str = command.split(" ");
                                            switch (str[0]) {
                                                case "send":
                                                    ctx.channel().writeAndFlush(new ChatRequestMessage(str[1], str[2]));
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }

                                    }).start();
                                }
                            });
                        }
                    })
                    .connect(new InetSocketAddress("localhost", 8080))
                    .sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
