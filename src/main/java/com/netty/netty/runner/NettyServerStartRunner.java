package com.netty.netty.runner;

import com.netty.netty.handler.IMHandler;
import com.netty.netty.handler.IMIdleStateHandler;
import com.netty.netty.handler.PacketCodecHandler;
import com.netty.netty.handler.Spliter;
import com.netty.netty.handler.group.CreateGroupHandler;
import com.netty.netty.zk.ZKServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Intro:  项目启动时，启动netty服务端
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/6
 * @Time: 下午5:56
 */
@Component
public class NettyServerStartRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(NettyServerStartRunner.class);

    @Autowired
    private ZKServiceRegistry zkServiceRegistry;

    @Override
    public void run(String... args) throws Exception {

        /**
         * 接收新的客户端链接线程组
         */
        NioEventLoopGroup boss = new NioEventLoopGroup();

        /**
         * 处理每条链接写入的数据线程组
         */
        NioEventLoopGroup worker = new NioEventLoopGroup();

        /**
         * 引导服务端启动工作类
         */
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                //.group 配置线程组
                .group(boss, worker)
                //.channel 指定服务端的IO模型，NioServerSocketChannel为NIO模型
                .channel(NioServerSocketChannel.class)
                //.childHandler 定义后续每条链接的数据读写，NioSocketChannel同理定义NIO模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(new CreateGroupHandler());
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                })
                //指定服务端启动过程中的一些逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel serverCh) throws Exception {
                    }
                })
                //.childOption开启tcp底层相关的属性，so_keepAlive true 表示底层开启心跳协议
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //.option 服务端设置属性，so_backlog表示系统用于临时存放已完成三次握手的请求队列的最大长度，如果连接建立频繁，可以扩大该值。
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.TCP_NODELAY, true);

        //.addListener 给返回结果ChannelFuture绑定监听器，用户判断端口是否绑定成功
        serverBootstrap.bind("127.0.0.1", 8000).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    logger.info("端口绑定成功");
                    if (zkServiceRegistry != null) {
                        zkServiceRegistry.register("127.0.0.1:8000");
                    }
                } else {
                    //查看错误数据
                    logger.error(future.cause().getMessage());
                    logger.info("端口绑定失败");
                    //失败后可重新绑定
                }
            }
        });
    }
}
