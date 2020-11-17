package com.value;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-11 20:43
 */
@Data
@Component
@RefreshScope
public class MailConfig {
    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;

}
