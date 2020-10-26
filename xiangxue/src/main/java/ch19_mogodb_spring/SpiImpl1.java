package ch19_mogodb_spring;

public class SpiImpl1 implements SpiService {
    @Override
    public void execute() {
        System.out.println("SPI first test");
    }
}
