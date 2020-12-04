package com;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * 86150
 * RedisLock
 * 2020/12/4 14:20
 */
public class RedisLock implements Lock {
    private JedisConnectionFactory factory;
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public void lock() {
        if (tryLock()) {
            return;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        // TODO
        String rtn = jedis.set("LOCK_KEY", uuid, "NX", "PX", 1000);
        if ("OK".equals(rtn)) {
            threadLocal.set(uuid);
            return true;
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try {
            String lua = Files.lines(Paths.get("unlock.lua")).collect(Collectors.joining());
            Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
            jedis.eval(lua, Arrays.asList("LOCK_KEY"), Arrays.asList(threadLocal.get()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
