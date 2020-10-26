package ch11_mybatis;

/**
 * 86150
 * MybatisTest
 * 2020/4/10 19:36
 */
public class MybatisTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        SqlSessionFactory factory = new SqlSessionFactory(configuration);
        SqlSession sqlSession = factory.openSession();
        System.out.println(sqlSession);
    }
}
