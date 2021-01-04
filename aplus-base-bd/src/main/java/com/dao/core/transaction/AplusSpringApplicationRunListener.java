package com.dao.core.transaction;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 17:41
 */
public class AplusSpringApplicationRunListener implements SpringApplicationRunListener {
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        AopConfigUtils.registerAutoProxyCreatorIfNecessary((BeanDefinitionRegistry) context);
        AopConfigUtils.forceAutoProxyCreatorToUseClassProxying((BeanDefinitionRegistry) context);
    }
}
