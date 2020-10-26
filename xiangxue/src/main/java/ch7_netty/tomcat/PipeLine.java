package ch7_netty.tomcat;

/**
 * 管道
 */
public interface PipeLine {
    Value getFirst();

    Value getBase();

    void setBase(Value value);

    void addValue(Value value);
}
