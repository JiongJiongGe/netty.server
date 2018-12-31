package com.netty.netty.handler.heart;

import com.netty.netty.pack.heart.HeartRequestPacket;
import com.netty.netty.pack.heart.HeartResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Intro: 服务端监听心跳
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/6
 * @Time: 上午10:42
 */
@ChannelHandler.Sharable
public class HeartTimeRequestHandler extends SimpleChannelInboundHandler<HeartRequestPacket>{

    private static Logger logger = LoggerFactory.getLogger(HeartTimeRequestHandler.class);

    public static HeartTimeRequestHandler INSTANCE = new HeartTimeRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartRequestPacket heartRequestPacket) throws Exception {
        logger.info("服务端收到一个心跳");
        channelHandlerContext.writeAndFlush(new HeartResponsePacket());
    }
}
