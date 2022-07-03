package com.hex.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author: hs
 */
@RestController
@RequestMapping("/testSentinel")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test-sentinel";
    }

    @GetMapping("/exception")
    public String exception() {
        Random random = new Random();
        if (random.nextInt(10) > 5) {
            int local = 1 / 0;
        }
        return "test-exception";
    }

}
