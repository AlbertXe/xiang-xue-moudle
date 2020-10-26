package ch7_netty.tomcat;

/**
 * 86150
 * Demo
 * 2020/3/12 23:09
 */
public class Demo {
    public static void main(String[] args) {
        String request = "this is a request";

        StandardPipeLine standardPipeLine = new StandardPipeLine();

        StandardValue standardValue = new StandardValue();
        FirstValue firstValue = new FirstValue();
        SecondValue secondValue = new SecondValue();

        standardPipeLine.setBase(standardValue);
        standardPipeLine.addValue(firstValue);
        standardPipeLine.addValue(secondValue);

        standardPipeLine.getFirst().invoke(request);
    }
}
