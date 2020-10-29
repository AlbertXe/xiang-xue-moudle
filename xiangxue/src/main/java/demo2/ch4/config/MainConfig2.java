package demo2.ch4.config;

import demo2.ch4.dao.TestDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"demo2.ch4.controller", "demo2.ch4.service", "demo2.ch4.dao"})
public class MainConfig2 {

    @Bean("testDao2")
//	@Primary
    TestDao testDao() {
        TestDao testDao = new TestDao();
        testDao.setFlag("2");
        return testDao;
    }
}
