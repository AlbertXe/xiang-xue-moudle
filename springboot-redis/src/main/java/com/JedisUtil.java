package com;

import redis.clients.jedis.Jedis;

/**
 * 86150
 * JedisUtil
 * 2020/12/4 3:40
 */
public class JedisUtil {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.101", 6379, 2000);
        System.out.println(jedis.ping());
    }
}
