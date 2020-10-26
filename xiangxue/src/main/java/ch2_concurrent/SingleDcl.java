package ch2_concurrent;

/**
 * 懒汉 双重检查
 * 86150
 * SingleDcl
 * 2020/2/21 12:34
 */
public class SingleDcl {
    //其实非线程安全  加个volatile ok
    static volatile SingleDcl singleDcl;

    public SingleDcl() {
    }

    public SingleDcl getInstance() {
        if (singleDcl == null) {
            synchronized (SingleDcl.class) {
                if (singleDcl == null) {
                    //new 会做三件事
                    // 1 分配空间 2 空间初始化 3 空间地址给引用
                    singleDcl = new SingleDcl();
                }
            }
        }
        return singleDcl;
    }
}
