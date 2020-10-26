package ch11_mybatis;

import lombok.AllArgsConstructor;

/**
 * 86150
 * DefaultSqlSession
 * 2020/4/10 20:20
 */
@AllArgsConstructor()
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    Executor executor;


    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = (ms, param) -> {
            //实际jdbc查询
            return null;
        };
    }

    @Override
    public Object selectOne() {
        return null;
    }
}
