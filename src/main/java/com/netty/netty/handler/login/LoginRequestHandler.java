package com.netty.netty.handler.login;

import com.netty.netty.domain.UserDo;
import com.netty.netty.env.EnvProperties;
import com.netty.netty.pack.login.LoginRequestPacket;
import com.netty.netty.pack.login.LoginResponsePacket;
import com.netty.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @Intro:  登录请求handler
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/10/19
 * @Time: 下午5:32
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket>{

    private static Logger logger = LoggerFactory.getLogger(LoginRequestHandler.class);

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        //返回packet
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (vail(loginRequestPacket)) {
            String userId = UUID.randomUUID().toString();
            logger.info("用户: {}, 开始登录，Id为: {}", loginRequestPacket.getUsername(), userId);
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setName(loginRequestPacket.getUsername());
            loginResponsePacket.setSucess(true);
            SessionUtil.channelGroup.add(channelHandlerContext.channel());
        } else {
            loginResponsePacket.setSucess(false);
            loginResponsePacket.setMessage("用户名或者密码错误");
        }
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    /**
     * 验证登录
     *
     * @param loginRequestPacket
     * @return
     */
    private boolean vail(LoginRequestPacket loginRequestPacket) {
        if (EnvProperties.applicationPassword.equals(loginRequestPacket.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
