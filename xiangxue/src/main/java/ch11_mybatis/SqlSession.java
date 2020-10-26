package ch11_mybatis;

public interface SqlSession<T> {
    T selectOne();
}
