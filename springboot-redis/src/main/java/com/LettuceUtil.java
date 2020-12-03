package com;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * 86150
 * LecttureUtil
 * 2020/12/4 3:52
 */
public class LettuceUtil {
    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create("redis://@192.168.1.101:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        connection.close();
        redisClient.shutdown();
    }
}
