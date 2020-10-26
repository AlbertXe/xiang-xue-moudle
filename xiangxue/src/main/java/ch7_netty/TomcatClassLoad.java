package ch7_netty;

import com.sun.nio.zipfs.ZipPath;
import org.junit.Test;
import sun.misc.Launcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 不同的加载器  优先父加载器加载
 * 86150
 * TomcatClassLoad
 * 2020/3/13 20:47
 */
public class TomcatClassLoad extends ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Map map = new HashMap();
        //这个加载器是C++写的 bootstrap ClassLoad
        System.out.println("map class load:" + map.getClass().getClassLoader());
        //ExtClassLoader
        System.out.println("zip class load:" + ZipPath.class.getClassLoader());
        //AppClassLoader
        System.out.println("TomcatClassLoad class load:" + TomcatClassLoad.class.getClassLoader());

        TomcatClassLoad tomcatClassLoad = new TomcatClassLoad();
        Object o = tomcatClassLoad.loadClass("ch7_netty.TomcatClassLoad").newInstance();
        System.out.println("这两个类是否相等:" + (o instanceof TomcatClassLoad));
        System.out.println("这两个类是否相等:" + (o == tomcatClassLoad));
        System.out.println("o class load:" + o.getClass().getClassLoader());

    }

    /**
     * 打破双亲委派
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
        InputStream is = getClass().getResourceAsStream(filename);
        if (is == null) {
            return super.loadClass(name);
        }
        try {
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 各个加载器加载
     */
    @Test
    public void test() {
        System.out.println("bootstrap classload:");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        Arrays.stream(urLs).forEach(System.out::println);

        System.out.println("ext classload:");
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader().getParent();
        URL[] urLs1 = urlClassLoader.getURLs();
        Arrays.stream(urLs1).forEach(System.out::println);


    }
}
