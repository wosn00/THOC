package com.hex.vertx.demo;

import com.hex.vertx.demo.event.EventBusTestVerticle;
import com.hex.vertx.demo.verticle.HttpServerVerticle;
import io.vertx.core.Vertx;

/**
 * @author: hs
 */
public class Main {

    public static void main(String[] args) {

        testEventBus();

        testHttpServer();
    }

    private static void testHttpServer() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpServerVerticle());
    }

    private static void testEventBus() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new EventBusTestVerticle());

    }
}
