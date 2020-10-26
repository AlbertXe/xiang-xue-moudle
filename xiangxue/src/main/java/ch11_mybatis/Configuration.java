package ch11_mybatis;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 86150
 * Configuration
 * 2020/4/10 19:42
 */
@Getter
@Setter
public class Configuration {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
}
