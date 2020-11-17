package com.value;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-17 11:01
 */
public class RefreshConfigUtil {

    public static void updateConfig(AbstractApplicationContext context) {

        refreshConfig(context);

        BeanRefreshScope.getInstance().clean();
    }

    public static void refreshConfig(AbstractApplicationContext context) {
        Map<String, Object> info = new HashMap<>();
        info.put("mail.host", "localhost" + RandomUtils.nextInt());
        info.put("mail.username", "root" + RandomUtils.nextInt());
        info.put("mail.password", "root" + RandomUtils.nextInt());

        MapPropertySource source = new MapPropertySource("mail", info);
        context.getEnvironment().getPropertySources().addFirst(source);
    }
}
