package com.hex.vertx.demo.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

/**
 * @author guohs
 * @date 2021/12/23
 */
public class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);

        //匹配任意http请求，支持文件上传
        router.route().handler(BodyHandler.create());
        //匹配任意http请求，解码cookie放入到routeContext里，并将响应时将cookie写到response的header里
        router.route().handler(CookieHandler.create());
        //匹配任意http请求，为每次用户请求维护一个内存上的session
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        //handler() 是直接在eventLoop线程里执行
        router.get("/count").handler(this::handlerCount);
        //blockingHandler() 会将msg放入到workPool工作线程池里面异步执行,默认是有序，order为false是无序 并发执行
        router.get("/test").blockingHandler(this::handlerGet, false);

        //匹配/assets/*的请求，从asserts路径获取静态资源文件
        router.route("/assets/*").handler(StaticHandler.create("asserts"));

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        System.out.println("verticle 实例启动完成");
    }

    private void handlerCount(RoutingContext context) {
        Session session = context.session();
        Integer count = session.get("count");
        if (count == null) {
            count = 0;
        }
        session.put("count", ++count);
        context.response()
                .putHeader("content-type", "text/html")
                .end("total visit count:" + session.get("count"));
    }

    private void handlerGet(RoutingContext context) {
        System.out.println("收到http请求");
        String param1 = context.request().getParam("param1");
        String param2 = context.request().getParam("param2");

        String response = param1 + ":" + param2;
        context.response().end(response);
    }
}
