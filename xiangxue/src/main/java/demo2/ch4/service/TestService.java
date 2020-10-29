package demo2.ch4.service;

import demo2.ch4.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
//	@Qualifier("testDao")
//	@Resource
            TestDao testDao;

    public void printLn() {
        System.out.println(testDao);
    }
}
