package ch3_jvm;

/**
 * 自定义类加载器
 * 86150
 * MyClassLoader
 * 2020/2/23 18:37
 */
public class MyClassLoader extends ClassLoader {
    private String name;

    protected MyClassLoader(String name) {
        super();
        this.name = name;
    }

    public void setBasePath() {

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    /**
     * 具体的加密
     *
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        return new byte[4];
    }
}
