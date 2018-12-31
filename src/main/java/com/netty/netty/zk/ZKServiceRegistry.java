package com.netty.netty.zk;

import com.netty.netty.constant.ZKConstant;
import com.netty.netty.env.EnvProperties;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Intro: 服务端zk注入
 *
 * @Author: WangJiongDa(yunkai)
 * @Date: 2018/11/13
 * @Time: 下午2:45
 */
@Component
public class ZKServiceRegistry {

    private static Logger logger = LoggerFactory.getLogger(ZKServiceRegistry.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private Environment env;

    public void register(String dataAddress) {
        if (!StringUtils.isEmpty(dataAddress)) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                addRootNode(zk);
                createNode(zk, dataAddress);
            }
        }
    }

    /**
     * 建立连接
     *
     * @return
     */
    private ZooKeeper connectServer() {
        EnvProperties.ZK_REGISTRY_ADDRESS = env.getProperty("zk.registry.address");
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(EnvProperties.ZK_REGISTRY_ADDRESS, ZKConstant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    } else if (event.getState() == Event.KeeperState.Disconnected){
                       logger.error("zk 断开链接");
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            logger.error("", e);
        } catch (InterruptedException ex){
            logger.error("", ex);
        }
        return zk;
    }

    /**
     * 增加root 节点
     *
     * @param zk
     */
    private void addRootNode(ZooKeeper zk) {
        try {
            Stat stat = zk.exists(ZKConstant.ZK_NETTY_REGISTRY_PATH, false);
            if (stat == null) {
                zk.create(ZKConstant.ZK_NETTY_REGISTRY_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            logger.error("addRootNode error = {}", e);
        } catch (InterruptedException e) {
            logger.error("addRootNode error = {}", e);
        }
    }

    /**
     * 在root节点下，增加服务地址
     *
     * @param zk
     * @param dataAddress
     */
    private void createNode(ZooKeeper zk, String dataAddress) {
        try {
            byte[] bytes = dataAddress.getBytes();
            String path = zk.create(ZKConstant.ZK_NETTY_SERVER_REGISTRY_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException e) {
            logger.error("addRootNode error = {}", e);
        } catch (InterruptedException e) {
            logger.error("addRootNode error = {}", e);
        }
    }
}
