package com.hex.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.hex.dubbo.api.RpcServerTestService;
import com.hex.dubbo.entity.TestRequest;
import com.hex.dubbo.entity.TestResponse;
import org.springframework.stereotype.Component;

/**
 * @author: hs
 */
@Component
@Service(interfaceClass = RpcServerTestService.class)
public class RpcServerTestServiceImpl implements RpcServerTestService {

    @Override
    public TestResponse handler(TestRequest request) {

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new TestResponse().setResponse(request.getBody());
    }
}
