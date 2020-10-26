package ch7_netty.tomcat;

/**
 * 86150
 * FirstValue
 * 2020/3/12 22:59
 */
public class SecondValue implements Value {
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
        System.out.println("第二道阀门");
        next().invoke(handing);
    }
}
