package com.hex.vertx.demo.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author guohs
 * @date 2021/12/23
 */
public class RestfulVerticle extends AbstractVerticle {

    public void start() {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());



    }
}
