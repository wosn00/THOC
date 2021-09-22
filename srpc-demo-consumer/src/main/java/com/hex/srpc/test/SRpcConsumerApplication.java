package com.hex.srpc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hs
 */
@SpringBootApplication
@EnableSRpc
public class SRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SRpcConsumerApplication.class, args);
    }
}