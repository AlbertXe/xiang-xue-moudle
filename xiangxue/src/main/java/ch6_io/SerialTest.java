package ch6_io;

import ch1_concurrent.User;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化
 * 86150
 * SerialTest
 * 2020/3/6 21:24
 */
public class SerialTest {
    static String fileName = "D:\\a.txt";

    public static void main(String[] args) throws IOException {
        User user = new User("123", "xie");
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("albert");

        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));

        os.writeObject(user);
        os.writeObject(list);
        os.flush();
    }

    @Test
    public void read() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        User user = (User) in.readObject();
        List<String> list = (List<String>) in.readObject();
        System.out.println(user);
        System.out.println(list);
    }
}
