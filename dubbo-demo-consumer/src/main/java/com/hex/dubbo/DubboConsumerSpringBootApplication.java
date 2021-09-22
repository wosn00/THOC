package com.hex.dubbo;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hs
 */
@SpringBootApplication
@EnableDubboConfiguration
public class DubboConsumerSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerSpringBootApplication.class, args);

    }
}
