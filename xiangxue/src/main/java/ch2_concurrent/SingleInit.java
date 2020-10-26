package ch2_concurrent;

/**
 * 内部类单例 延时加载
 * 86150
 * SingleInit
 * 2020/2/21 12:44
 */
public class SingleInit {

    private static class Holder {
        static SingleInit singleInit = new SingleInit();
    }

    public SingleInit getInstance() {
        return Holder.singleInit;
    }

}
