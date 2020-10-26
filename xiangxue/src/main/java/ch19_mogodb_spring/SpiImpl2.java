package ch19_mogodb_spring;

public class SpiImpl2 implements SpiService {
    @Override
    public void execute() {
        System.out.println("SPI second test");
    }
}
