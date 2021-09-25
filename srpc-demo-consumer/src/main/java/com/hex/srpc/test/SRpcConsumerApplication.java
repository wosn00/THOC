package com.hex.srpc.test;

import com.hex.common.annotation.SRpcScan;
import com.hex.srpc.core.config.SRpcClientConfig;
import com.hex.srpc.core.rpc.Client;
import com.hex.srpc.core.rpc.client.SRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hs
 */
@SpringBootApplication
//@EnableSRpc
public class SRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SRpcConsumerApplication.class, args);
    }
}
