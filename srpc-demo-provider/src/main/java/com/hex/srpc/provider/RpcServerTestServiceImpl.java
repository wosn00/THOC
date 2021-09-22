package com.hex.srpc.provider;

import com.hex.common.annotation.RouteMapping;
import com.hex.common.annotation.SRpcRoute;
import com.hex.srpc.entity.TestRequest;
import com.hex.srpc.entity.TestResponse;

/**
 * @author: hs
 * <p>
 * SprcRoute服务1
 * 标注了@SRpcRoute注解后类似@Service，可使用spring相关注解@Autowired等
 */
@SRpcRoute
public class RpcServerTestServiceImpl {

    @RouteMapping("/test")
    public TestResponse handler(TestRequest request) {

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new TestResponse().setResponse(request.getBody());
    }
}
