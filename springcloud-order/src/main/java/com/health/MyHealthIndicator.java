package com.health;

import com.controller.OrderController;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;

/**
 * 86150
 * MyHealth
 * 2020/10/27 21:32
 */
@Configuration
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if (OrderController.db_check) {
            return Health.up().build();
        }
        return Health.down().build();
    }
}
