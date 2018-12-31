package com.netty.netty.service;

import com.netty.netty.domain.ConfigDo;

/**
 * @Intro:
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/9
 * @Time: 下午1:46
 */
public interface ConfigService {

    /**
     * 获得最新的config配置信息
     *
     * @return
     */
    ConfigDo geConfig();

    /**
     * 保存
     *
     * @param configMessage
     */
    void save(String configMessage);
}
