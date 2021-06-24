package com.example.ribbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
//@RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
//@RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
public class DirectReceiver {

    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    public void process1(Map testMessage) {
        System.out.println("1-DirectReceiver消费者收到消息  : " + testMessage.toString());
    }

    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    public void process2(Map testMessage) {
        System.out.println("2-DirectReceiver消费者收到消息  : " + testMessage.toString());
    }

    @RabbitListener(queues = "topic.man")//监听的队列名称 topic.man
    public void process3(Map testMessage) {
        System.out.println("1-TopicTotalReceiver消费者收到消息  : " + testMessage.toString());
    }

    @RabbitListener(queues = "topic.woman")//监听的队列名称 topic.woman
    public void process4(Map testMessage) {
        System.out.println("2-TopicTotalReceiver消费者收到消息  : " + testMessage.toString());
    }

    @RabbitListener(queues = "fanout.A")
    public void processA(Map testMessage) {
        System.out.println("FanoutReceiverA消费者收到消息  : " +testMessage.toString());
    }

    @RabbitListener(queues = "fanout.B")
    public void processB(Map testMessage) {
        System.out.println("FanoutReceiverB消费者收到消息  : " +testMessage.toString());
    }

    @RabbitListener(queues = "fanout.C")
    public void processC(Map testMessage) {
        System.out.println("FanoutReceiverC消费者收到消息  : " +testMessage.toString());
    }
}