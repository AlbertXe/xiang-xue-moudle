package ch4_jvm_mysql;

import ch1_concurrent.User;

/**
 * -XX:-DoEscapeAnalysis
 * 86150
 * AllocStack
 * 2020/2/24 20:54
 */
public class AllocStack {

    public static void alloc() {
        User user = new User("xie", "hh");
        System.out.println(user.getId());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            alloc();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
