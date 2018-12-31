package com.netty.netty.service.impl;

import com.google.gson.Gson;
import com.netty.netty.domain.ConfigDo;
import com.netty.netty.mapper.ConfigMapper;
import com.netty.netty.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Intro:
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/9
 * @Time: 下午1:46
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    private static Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private ConfigMapper configMapper;

    /**
     * 获得最新的config配置信息
     *
     * @return
     */
    @Override
    public ConfigDo geConfig() {
        return configMapper.geConfig();
    }

    /**
     * 保存
     *
     * @param configMessage
     */
    @Override
    public void save(String configMessage) {
        try {
            ConfigDo configDo = new ConfigDo();
            configDo.setConfigMessage(configMessage);
            configMapper.insert(configDo);
        } catch (Exception e) {
            logger.error("configMessage error : {}", e.getMessage(), e);
        }
    }
}
