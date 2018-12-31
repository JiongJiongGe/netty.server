package com.netty.netty.handler;

import com.netty.netty.handler.heart.HeartTimeRequestHandler;
import com.netty.netty.handler.login.LoginRequestHandler;
import com.netty.netty.handler.stop.ClientStopRequestHandler;
import com.netty.netty.pack.CommandInterface;
import com.netty.netty.pack.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Intro: 服务端整合handler
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/5
 * @Time: 下午7:57
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet>{

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(CommandInterface.LOGIN_REQUEST, LoginRequestHandler.INSTANCE);
        handlerMap.put(CommandInterface.HEART_REQUEST, HeartTimeRequestHandler.INSTANCE);
        handlerMap.put(CommandInterface.CLIENT_STOP_REQUEST, ClientStopRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(channelHandlerContext, packet);
    }
}
