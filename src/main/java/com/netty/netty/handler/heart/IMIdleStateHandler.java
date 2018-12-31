package com.netty.netty.handler.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Intro: 服务端空闲检测
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/6
 * @Time: 上午9:55
 */
public class IMIdleStateHandler extends IdleStateHandler{

    private static Logger logger = LoggerFactory.getLogger(IMIdleStateHandler.class);

    /**
     * 空闲时间
     */
    private static final int TIME_DE = 15;

    public IMIdleStateHandler() {
        super(TIME_DE, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        logger.info("{} 秒内未读取到数据，关闭连接", TIME_DE);
        ctx.channel().close();
    }
}
