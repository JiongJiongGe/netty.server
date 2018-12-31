package com.netty.netty.env;

import java.io.Serializable;

/**
 * @Intro:
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/7
 * @Time: 上午9:43
 */
public class EnvProperties implements Serializable{

    /**
     * 密码
     */
    public static String applicationPassword = "yk_wjd";

    /**
     * zk服务地址
     */
    public static String ZK_REGISTRY_ADDRESS;
}
