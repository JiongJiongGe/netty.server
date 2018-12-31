package com.netty.netty.constant;

/**
 * @Intro:  zk常量
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/13
 * @Time: 下午2:58
 */
public interface ZKConstant {

    /**
     * ZK session超时时间
     */
    int ZK_SESSION_TIMEOUT = 5000;

    /**
     * netty 注册地址
     */
    String ZK_NETTY_REGISTRY_PATH = "/netty";

    /**
     * netty 服务端注册地址
     */
    String ZK_NETTY_SERVER_REGISTRY_PATH = ZK_NETTY_REGISTRY_PATH + "/server";
}
