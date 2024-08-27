package com.intuit.foodorderingsystem.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/test")
@Log4j2
public class TestController {

    @GetMapping("/success")
    public String returnSuccess () {
        log.info("Started Loggggg");
        return "Success";
    }
}
