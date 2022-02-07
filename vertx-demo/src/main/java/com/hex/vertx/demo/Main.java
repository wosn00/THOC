package com.hex.vertx.demo;

import com.hex.vertx.demo.event.EventBusTestVerticle;
import com.hex.vertx.demo.verticle.HttpServerVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @author: hs
 */
public class Main {

    private static Vertx vertx;

    public static void main(String[] args) {
        // 初始化一个vertx实例
        vertx = Vertx.vertx(new VertxOptions().setBlockedThreadCheckInterval(99999999L));

//        testEventBus();

        testHttpServer();
    }

    private static void testHttpServer() {
        // HttpServerVerticle实例数量为16个，对应netty的workGroup线程组16个eventLoop线程
        // 服务器是多核的，部署多个实例来充分利用所有核心。不然默认实例是1，那么对应的eventLoop线程只有1个！！！
        vertx.deployVerticle(HttpServerVerticle.class.getName(), new DeploymentOptions().setInstances(16));
    }

    private static void testEventBus() {

        vertx.deployVerticle(new EventBusTestVerticle());
    }
}
