package com.hex.srpc;

import com.hex.common.annotation.SRpcScan;
import com.hex.srpc.core.config.SRpcServerConfig;
import com.hex.srpc.core.rpc.server.SRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hs
 */
@SpringBootApplication
//@EnableSRpc
@SRpcScan
public class SRpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SRpcProviderApplication.class, args);

        // 启动服务端, 需填入rpc服务端配置, 可使用默认配置, source填写有@RouteScan注解的类
        SRpcServer.builder()
                .serverConfig(new SRpcServerConfig().setCompressEnable(false).setBusinessThreads(500))
                .sourceClass(SRpcProviderApplication.class)
                .port(9957)
                .start();
    }
}
