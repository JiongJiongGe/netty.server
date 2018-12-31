package com.netty.netty.runner;

import com.netty.netty.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @Intro: tomcat/应用程序关闭时触发
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/7
 * @Time: 下午8:08
 */
@Component
public class ServerStopRunner {

    private static Logger logger = LoggerFactory.getLogger(ServerStopRunner.class);

    @PreDestroy
    public void destory() {
        logger.info("destory ... ");
        if (SessionUtil.channelGroup != null) {
            SessionUtil.channelGroup = null;
        }
    }
}
