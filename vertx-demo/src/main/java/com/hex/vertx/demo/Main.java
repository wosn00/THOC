package com.hex.vertx.demo;

import com.hex.vertx.demo.verticle.RestfulVerticle;
import io.vertx.core.Vertx;

/**
 * @author: hs
 */
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RestfulVerticle());


    }
}
