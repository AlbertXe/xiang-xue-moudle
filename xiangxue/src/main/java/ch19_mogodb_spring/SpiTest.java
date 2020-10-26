package ch19_mogodb_spring;

import org.junit.Test;
import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {

    @Test
    public void test() {
        ServiceLoader<SpiService> load = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> iterator = load.iterator();
        while (iterator.hasNext()) {
            iterator.next().execute();
        }
    }

    @Test
    public void test2() {
        Iterator<SpiService> providers = Service.providers(SpiService.class);
        while (providers.hasNext()) {
            providers.next().execute();
        }
    }
}
