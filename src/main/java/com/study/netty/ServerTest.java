package com.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;


/**
 * @author liaozhiqiang1
 * @date 2021/10/18 15:21
 * @Description
 */
@Slf4j
public class ServerTest {

    public static void main(String[] args) {
        //NioEventLoopGroup 实现自 线程池(每个线程维护一个selector选择器),可做IO线程池、也可以做普通线程池和定时线程池
        //BossEventLoop负责客户端的连接  nThreads线程池大小（默认为计算机内核 * 2）
        NioEventLoopGroup bossEventLoop = new NioEventLoopGroup();
        //WorkerEventLoop 负责读写IO操作  nThreads线程池大小（默认为计算机内核 * 2）
        NioEventLoopGroup workerEventLoop = new NioEventLoopGroup();
        //defaultEventLoop 业务处理线程  可做普通线程池和定时线程池
        DefaultEventLoopGroup defaultEventLoop = new DefaultEventLoopGroup();
        //启动器 负责组装netty组件，启动服务器
        new ServerBootstrap()
                //BossEventLoop,WorkerEventLoop ,group
                .group(bossEventLoop, workerEventLoop)
                //选择 服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //WorkerEventLoop 执行那些处理器（handler）
                .childHandler(
                        //Channel代表和客户端进行数据读写的通道  Initializer 初始化，负责添加别的处理器（handler）
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                //添加handler
                                //添加字符串解码处理器 将ByteBuf转换为字符串
                                //ch.pipeline().addLast(new StringDecoder());
                                //自定义handler  入栈处理器  需要继承ChannelInboundHandlerAdapter类  业务代码块由defaultEventLoop线程池执行，避免阻塞IO线程池
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        super.channelRead(ctx, msg);//或者 ctx.fireChannelRead(msg);
                                    }
                                });
                                ch.pipeline().addLast(defaultEventLoop, new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        ByteBuf buf = ((ByteBuf) msg);
                                        //ByteBuf byteBuf = ctx.alloc().buffer();
                                        ctx.writeAndFlush(buf.toString(Charset.defaultCharset()));
                                        super.write(ctx, msg, promise);
                                    }
                                });
                                ch.pipeline().addLast(defaultEventLoop, new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        Thread.sleep(1000);
                                        ByteBuf buf = ((ByteBuf) msg);
                                        log.info("收到客户端消息： 【{}】", buf.toString(Charset.defaultCharset()));
                                        ctx.writeAndFlush(buf);
                                    }
                                });
                            }
                        })
                //绑定监听的端口
                .bind(9000);
    }
}
