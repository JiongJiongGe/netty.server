package com.netty.netty.mapper;

import com.netty.netty.domain.ConfigDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Intro:
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/9
 * @Time: 下午1:44
 */
@Mapper
@Component
public interface ConfigMapper {

    ConfigDo geConfig();

    void insert(ConfigDo configDo);
}
