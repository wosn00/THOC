package com.hex.srpc.test.consumer;

import com.hex.common.net.HostAndPort;
import com.hex.srpc.core.config.SRpcClientConfig;
import com.hex.srpc.core.rpc.Client;
import com.hex.srpc.core.rpc.client.SRpcClient;
import com.hex.srpc.test.api.RpcServerTestService;
import com.hex.srpc.test.entity.TestRequest;
import com.hex.srpc.test.entity.TestResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

/**
 * @author: hs
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

//    @Autowired
//    private RpcServerTestService testService;

    private Client client;

    @PostConstruct
    public void initClient() {
        // 初始化客户端，需填入rpc客户端配置，可使用默认配置
        client = SRpcClient.builder()
                .config(new SRpcClientConfig().setConnectionSizePerNode(1).setCompressEnable(false))
                .start();
    }

    @GetMapping("/test")
    public void testDubbo(int threadNum, int loopTimes) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(threadNum);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                TestRequest testRequest = new TestRequest().setName("zhangsan");
                String s = RandomStringUtils.randomAlphanumeric(100);
                testRequest.setBody(s);
                for (int j = 0; j < loopTimes; j++) {
//                    TestResponse handler = testService.handler(testRequest);
                    TestResponse invoke = client.invoke("/test", testRequest, TestResponse.class, HostAndPort.from("127.0.0.1:9957"));
                    if (!invoke.getResponse().equals(s)) {
                        throw new RuntimeException();
                    }
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        long cost = System.currentTimeMillis() - start;

        float tps = (float) threadNum * loopTimes / (float) cost * 1000;

        logger.info("========总耗时:{}=======", cost);
        logger.info("========tps:{}/s=========", tps);

    }

}
