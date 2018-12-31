package com.netty.netty.controller.config;

import com.netty.netty.pack.config.ConfigRequestPacket;
import com.netty.netty.service.ConfigService;
import com.netty.netty.util.SessionUtil;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Intro: 配置controller
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/6
 * @Time: 下午5:45
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigService configService;

    private ThreadPoolExecutor packetExecutor = new ThreadPoolExecutor(4, 8, 50, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * 设置信息
     *
     * @param configParam
     * @return
     */
    @RequestMapping(value = "/set")
    public String set(String configParam, String configVersion) {
//        ConfigRequestPacket packet = new ConfigRequestPacket();
//        packet.setConfigParam(configParam);
//        packet.setConfigVersion(configVersion);
//        ChannelGroup channelGroup = SessionUtil.channelGroup;
//        if (channelGroup != null) {
//            channelGroup.writeAndFlush(packet);
//        }
//        configService.save(configParam);

        //模拟多线程发送消息
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            packetExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("finalI = {}", finalI);
                    ConfigRequestPacket packet = new ConfigRequestPacket();
                    packet.setConfigParam("message" + finalI);
                    packet.setConfigVersion(finalI + "");
                    ChannelGroup channelGroup = SessionUtil.channelGroup;
                    if (channelGroup != null) {
                        channelGroup.writeAndFlush(packet);
                    }
                }
            });
        }
        return "success";
    }
}
