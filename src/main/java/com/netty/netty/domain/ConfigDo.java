package com.netty.netty.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Intro: 配置表
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/9
 * @Time: 下午1:33
 */
@Data
public class ConfigDo implements Serializable{

    private Integer id;

    /**
     * 配置信息
     */
    private String configMessage;
}
