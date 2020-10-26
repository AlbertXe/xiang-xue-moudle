package ch7_netty.tomcat;

/**
 * 86150
 * StandardPipeLine
 * 2020/3/12 23:01
 */
public class StandardPipeLine implements PipeLine {
    protected Value first;
    protected Value base;

    @Override
    public Value getFirst() {
        return first;
    }

    @Override
    public Value getBase() {
        return base;
    }

    @Override
    public void setBase(Value value) {
        base = value;
    }

    @Override
    public void addValue(Value value) {
        if (first == null) {
            first = value;
            value.setNext(base);
        } else {
            Value curr = first;
            while (curr != null) {
                if (curr.next() == base) {
                    curr.setNext(value);
                    value.setNext(base);
                }
                curr = curr.next();
            }
        }
    }
}
