package ch11_mybatis;

public interface Executor<T> {
    T query(MappedStatement ms, Object param);
}
