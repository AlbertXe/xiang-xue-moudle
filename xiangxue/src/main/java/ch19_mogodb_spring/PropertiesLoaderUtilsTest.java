package ch19_mogodb_spring;

import org.junit.Test;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesLoaderUtilsTest {
    @Test
    public void test() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("META-INF/a.properties");
        System.out.println(properties.getProperty("aaa"));
    }

    @Test
    public void test2() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("beans.xml");
    }

    /**
     * 加载其他jar包文件
     *
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("META-INF/spring.handlers");
        System.out.println(properties.getProperty("http://www.springframework.org/schema/c"));
    }

    @Test
    public void test4() throws IOException {
        Properties properties = new Properties();
        URL resource = this.getClass().getResource("/META-INF/a.xml");
        InputStream inputStream = resource.openConnection().getInputStream();
        properties.loadFromXML(inputStream);

        System.out.println(properties);
    }
}
