package com.hex.srpc.api;


import com.hex.common.annotation.Mapping;
import com.hex.srpc.entity.TestRequest;
import com.hex.srpc.entity.TestResponse;

/**
 * @author: hs
 * <p>
 * 指定节点列表
 */
//@SRpcClient(nodes = {"127.0.0.1:9957"})
public interface RpcServerTestService {

    @Mapping("/test")
    TestResponse handler(TestRequest request);
}
