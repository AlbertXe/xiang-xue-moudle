package ch1_concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 86150
 * UseAtomicRefrence
 * 2020/2/18 19:44
 */
public class UseAtomicRefrence {
    private static AtomicReference<User> atomicReference;

    public static void main(String[] args) {
        User user = new User("1", "java");
        atomicReference = new AtomicReference<>(user);
        User user1 = new User("2", "python");
        atomicReference.compareAndSet(user, user1);

        System.out.println(atomicReference.get());

    }

}
