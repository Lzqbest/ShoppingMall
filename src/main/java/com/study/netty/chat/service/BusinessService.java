package com.study.netty.chat.service;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaozhiqiang1
 * @date 2021/10/22 9:40
 * @Description
 */
public class BusinessService {
    /**
     * 单例service
     */
    private static BusinessService instance;

    /**
     * 获取service单例对象
     *
     * @return
     */
    public static BusinessService getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (BusinessService.class) {
                if (Objects.isNull(instance)) {
                    instance = new BusinessService();
                }
            }
        }
        return instance;
    }

    private BusinessService() {
    }

    private Map<String, Channel> SESSION_STR_MAP = new ConcurrentHashMap<>();
    private Map<Channel, String> SESSION_CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param channel
     * @return
     */
    public boolean login(String username, String password, Channel channel) {
        SESSION_STR_MAP.put(username, channel);
        SESSION_CHANNEL_MAP.put(channel, username);
        return true;
    }

    /**
     * 获取用户NioSocketChannel
     *
     * @param username
     * @return
     */
    public Channel getUserChannel(String username) {
        return SESSION_STR_MAP.get(username);
    }

    public String getUserName(Channel channel) {
        return SESSION_CHANNEL_MAP.get(channel);
    }
}
