package com.hex.srpc.test.api;


import com.hex.common.annotation.RouteBody;
import com.hex.common.annotation.RouteMapping;
import com.hex.rpc.sping.annotation.SRpcClient;
import com.hex.srpc.test.entity.TestRequest;
import com.hex.srpc.test.entity.TestResponse;

/**
 * @author: hs
 * <p>
 * 指定节点列表
 */
@SRpcClient(nodes = {"127.0.0.1:9957"})
public interface RpcServerTestService {

    @RouteMapping("/test")
    TestResponse handler(@RouteBody TestRequest request);
}
