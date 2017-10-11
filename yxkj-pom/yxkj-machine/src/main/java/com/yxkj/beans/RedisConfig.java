package com.yxkj.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {
    @Value("${redis.ip}")
    private String jedisIp;
    @Value("${redis.port}")
    private int jedisPort;
    @Value("${redis.requirepass}")
    private String jedisPassword;
    @Value("${redis.timeout}")
    private int jedisTimeout;

    public String getJedisIp() {
        return jedisIp;
    }

    public void setJedisIp(String jedisIp) {
        this.jedisIp = jedisIp;
    }

    public int getJedisPort() {
        return jedisPort;
    }

    public void setJedisPort(int jedisPort) {
        this.jedisPort = jedisPort;
    }

    public String getJedisPassword() {
        return jedisPassword;
    }

    public void setJedisPassword(String jedisPassword) {
        this.jedisPassword = jedisPassword;
    }

    public int getJedisTimeout() {
        return jedisTimeout;
    }

    public void setJedisTimeout(int jedisTimeout) {
        this.jedisTimeout = jedisTimeout;
    }
}
