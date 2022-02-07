package com.hex.vertx.demo.event;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * @author guohs
 * @date 2022/1/4
 */
public class EventBusTestVerticle extends AbstractVerticle {

    public static String ADDRESS = "test.address";

    @Override
    public void start() throws Exception {
        //注册消费者
        eventBusConsumer();

        Thread.sleep(2000);

        //广播方式发送消息
        publishMessage();

        //集群方式发送消息
        sendMessage();
    }

    private void sendMessage() {
        EventBus eventBus = vertx.eventBus();
        eventBus.send(ADDRESS, "send a message...");
        System.out.println("发送完成");
    }

    private void publishMessage() {
        EventBus eventBus = vertx.eventBus();
        eventBus.publish(ADDRESS, "publish a message...");
    }

    private void eventBusConsumer() {

        EventBus eventBus = vertx.eventBus();
        //创建一个消费者，但是还没被注册
        MessageConsumer<String> consumer = eventBus.consumer(ADDRESS);

        //注册handler，收到消息时，就会调用handler处理内容
        consumer.handler(message -> {
            System.out.println(message.address() + ":收到事件" + message.body());
            message.reply("事件处理完毕");
        });

        //消费者集群注册完成时的回调
        consumer.completionHandler(res -> {
            if (res.succeeded()) {
                System.out.println("注册成功");
            } else {
                System.out.println("注册失败");
            }
        });

    }
}
