package ch7_netty.tomcat;

/**
 * 其实就是节点
 * 86150
 * StandardValue
 * 2020/3/12 22:56
 */
public class StandardValue implements Value {
    protected Value next;

    @Override
    public Value next() {
        return next;
    }

    @Override
    public void setNext(Value value) {
        next = value;
    }

    @Override
    public void invoke(String handing) {
        handing = handing + " 基础阀门";
        System.out.println(handing);
    }
}
