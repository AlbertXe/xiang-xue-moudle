package com.dao.venus.event;

import com.google.common.eventbus.EventBus;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 15:31
 */
public final class ShardingEventBusInstance {
    private static final EventBus INSTANCE = new EventBus();

    public static EventBus getInstance() {
        return INSTANCE;
    }

    private ShardingEventBusInstance() {
    }
}
