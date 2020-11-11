package com.value;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-11 20:00
 */
@Configurable //自动装配 可以让 new Object() 里面的依赖自动注入进来,解决了 null指针问题
@ComponentScan
@PropertySource({"classpath:com.value/db.properties"})
public class DbConfigImport {

}
