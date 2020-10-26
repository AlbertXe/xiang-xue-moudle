package demo2.aop;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {

    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
        UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.addUser();
    }
}
