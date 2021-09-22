package com.hex.dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hex.dubbo.api.RpcServerTestService;
import com.hex.dubbo.entity.TestRequest;
import com.hex.dubbo.entity.TestResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * @author: hs
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Reference(url = "dubbo://192.168.1.2:20880")
    private RpcServerTestService testService;

    @GetMapping("/test")
    public void testDubbo(int threadNum, int loopTimes) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(threadNum);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                TestRequest testRequest = new TestRequest().setName("zhangsan");
                testRequest.setBody(RandomStringUtils.randomAlphanumeric(100));
                for (int j = 0; j < loopTimes; j++) {
                    TestResponse handler = testService.handler(testRequest);
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
