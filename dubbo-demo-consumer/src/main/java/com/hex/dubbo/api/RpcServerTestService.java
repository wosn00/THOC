package com.hex.dubbo.api;


import com.hex.dubbo.entity.TestRequest;
import com.hex.dubbo.entity.TestResponse;

/**
 * @author: hs
 * <p>
 * 指定节点列表
 */
public interface RpcServerTestService {

    TestResponse handler(TestRequest request);
}
