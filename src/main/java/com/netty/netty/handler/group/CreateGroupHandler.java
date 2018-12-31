package com.netty.netty.handler.group;

import com.netty.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Intro: 创建channelGroup handler
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/7
 * @Time: 下午8:22
 */
@ChannelHandler.Sharable
public class CreateGroupHandler extends ChannelInboundHandlerAdapter{

    private static Logger logger = LoggerFactory.getLogger(CreateGroupHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.channelGroup == null) {
            SessionUtil.channelGroup = new DefaultChannelGroup(ctx.executor());
        } else {
            //当channelGroup已经存在时，则移除此handler
            ctx.pipeline().remove(this);
        }
        super.channelActive(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        logger.info("channelGroup is exist, CreateGroupHandler remove");
    }
}
