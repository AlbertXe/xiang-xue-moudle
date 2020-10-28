package com.config;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Configuration;

/**
 * 86150
 * RibbonConfig
 * 2020/10/28 21:40
 */
@Configuration
public class RibbonConfig {
    //    @Bean
    public IClientConfig defaultIClientConfig() {
        DefaultClientConfigImpl config = new DefaultClientConfigImpl();
        config.loadDefaultValues();

        return config;
    }

    /**
     * 每次调用前 判断服务是否存活
     *
     * @return
     */
//    @Bean
    public IPing iPing() {
        return new PingUrl();
    }
}
