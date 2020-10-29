package com;

import com.service.TicketService;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 86150
 * MyTest
 * 2020/10/29 20:43
 */
@SpringBootTest(classes = ConsumerApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MyTest {

    @Autowired
    TicketService ticketService;

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 10, threads = 10)
    public void test() {
        System.out.println(1);
//        ticketService.qryTicket();
    }
}
