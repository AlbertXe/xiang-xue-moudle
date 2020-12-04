package com;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void test() {
        Jedis jedis = new Jedis("192.168.1.101", 6379);
        Map<String, String> order1 = new HashMap<>();
        order1.put("orderId", "1");
        order1.put("money", "10");
        order1.put("time", "20201020");
        Map<String, String> order2 = new HashMap<>();
        order2.put("orderId", "2");
        order2.put("money", "20");
        order2.put("time", "20201020");
        Map<String, String> order3 = new HashMap<>();
        order3.put("orderId", "3");
        order3.put("money", "30");
        order3.put("time", "20201020");
        jedis.hmset("order:1", order3);
        jedis.hmset("order:2", order3);
        jedis.hmset("order:3", order3);

        jedis.lpush("user:1:order", "order:1", "order:2", "order:3");

        Map<String, String> order4 = new HashMap<>();
        order4.put("orderId", "4");
        order4.put("money", "40");
        order4.put("time", "20201020");
        jedis.hmset("order:4", order4);

        jedis.lpush("user:1:order", "order:4");

        List<String> list = jedis.lrange("user:1:order", 0, -1);
        for (String s : list) {
            List<String> result = jedis.hmget(s, "orderId", "money", "time");
            System.out.println(result);
        }
    }
}
