package com.study.netty.chat;

import com.study.netty.chat.common.protocol.MessageCodec;
import com.study.netty.chat.entity.*;
import com.study.netty.chat.service.BusinessService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 9:22
 * @Description
 */
@Slf4j
public class ChatService {

    public static void main(String[] args) {
        //BossEventLoop负责客户端的连接  nThreads线程池大小（默认为计算机内核 * 2）
        EventLoopGroup bossEventLoop = new NioEventLoopGroup(1);
        //BossEventLoop负责读写IO操作  nThreads线程池大小（默认为计算机内核 * 2）
        EventLoopGroup workEventLoop = new NioEventLoopGroup(4);
        new ServerBootstrap()
                .group(bossEventLoop, workEventLoop)
                //  Linux中 通过/proc/sys/net/core/somaxconn 设置最大全连接队列数
                //  netty中 通过ChannelOption.SO_BACKLOG 设置最大全连接队列数  所以队列数的值应该较大
                .option(ChannelOption.SO_BACKLOG,1024)
                //设置服务器ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //WorkerEventLoop 执行那些处理器（handler）
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
                        ch.pipeline().addLast(new ChannelDuplexHandler() {
                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                if (evt instanceof IdleStateEvent) {
                                    IdleStateEvent e = (IdleStateEvent) evt;
                                    //读空闲事件
                                    if (e.state() == IdleState.READER_IDLE) {
                                        log.error("长时间未连接，通道关闭！");
                                        ctx.channel().close();
                                    }
                                }
                                super.userEventTriggered(ctx, evt);
                            }
                        });
                        //根据消息中长度字段的值动态拆分接收的字节缓冲区  解决粘包半包问题
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                        ch.pipeline().addLast(new MessageCodec());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<PingRequestMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, PingRequestMessage msg) throws Exception {
                                log.debug("收到客户端心跳包！");
                                ctx.channel().writeAndFlush(new PongResponseMessage());
                            }
                        });
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                                log.info("{}", msg);
                                boolean login = BusinessService.getInstance().login(msg.getUserName(), msg.getPassword(), ctx.channel());
                                LoginResponseMessage message;
                                if (login) {
                                    message = new LoginResponseMessage(true, "登录成功！");
                                } else {
                                    message = new LoginResponseMessage(false, "登录失败！");
                                }
                                ctx.channel().writeAndFlush(message);
                            }
                        });
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ChatRequestMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
                                Channel userChannel = BusinessService.getInstance().getUserChannel(msg.getTo());
                                String from = BusinessService.getInstance().getUserName(ctx.channel());
                                if (Objects.nonNull(userChannel)) {
                                    userChannel.writeAndFlush(new ChatResponseMessage(true, "操作成功！", from, msg.getContent()));
                                } else {
                                    ctx.channel().writeAndFlush(new ChatResponseMessage(false, "用户不在线!"));
                                }
                            }
                        });
                    }
                })
                //绑定端口
                .bind(8080);

    }
}
