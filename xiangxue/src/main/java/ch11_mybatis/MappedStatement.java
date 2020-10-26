package ch11_mybatis;

import lombok.Getter;
import lombok.Setter;

/**
 * 86150
 * MapperStatement
 * 2020/4/10 19:41
 */
@Getter
@Setter
public class MappedStatement {
    private String namespace;
    private String sourceId;
    private String sql;
    private String resultType;
}
