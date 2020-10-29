package demo2.ch4.controller;

import demo2.ch4.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @Autowired
    TestService testService;
}
