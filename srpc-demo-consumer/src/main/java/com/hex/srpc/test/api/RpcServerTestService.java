package com.hex.srpc.test.api;


import com.hex.srpc.entity.TestRequest;
import com.hex.srpc.entity.TestResponse;

/**
 * @author: hs
 * <p>
 * 指定节点列表
 */

public interface RpcServerTestService {

    TestResponse handler(TestRequest request);
}
