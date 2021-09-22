package com.hex.srpc;

import com.hex.rpc.sping.annotation.EnableSRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hs
 */
@SpringBootApplication
@EnableSRpc
public class SRpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SRpcProviderApplication.class, args);
    }
}
