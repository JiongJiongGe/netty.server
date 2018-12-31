package com.netty.netty;

import com.netty.netty.env.EnvProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NettyApplication {

	private static Logger logger = LoggerFactory.getLogger(NettyApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NettyApplication.class, args);
	}
}
