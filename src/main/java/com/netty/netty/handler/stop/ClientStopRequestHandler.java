package com.netty.netty.handler.stop;

import com.netty.netty.pack.stop.ClientStopRequestPacket;
import com.netty.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Intro: 客户端应用程序停止时 通知服务端handler
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/8
 * @Time: 下午4:20
 */
public class ClientStopRequestHandler extends SimpleChannelInboundHandler<ClientStopRequestPacket>{

    private static Logger logger = LoggerFactory.getLogger(ClientStopRequestHandler.class);

    public static final ClientStopRequestHandler INSTANCE = new ClientStopRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ClientStopRequestPacket clientStopRequestPacket) throws Exception {
        ChannelGroup channelGroup = SessionUtil.channelGroup;
        if (channelGroup.contains(channelHandlerContext.channel())) {
            channelGroup.remove(channelHandlerContext.channel());
        }
    }
}
