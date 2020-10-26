package ch7_netty.tomcat;

/**
 * 阀门
 */
public interface Value {

    Value next();

    void setNext(Value value);

    void invoke(String handing);
}
