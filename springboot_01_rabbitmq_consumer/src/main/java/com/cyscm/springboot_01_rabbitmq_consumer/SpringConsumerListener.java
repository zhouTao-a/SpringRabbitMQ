package com.cyscm.springboot_01_rabbitmq_consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoutao
 */
@Component
public class SpringConsumerListener {

    @RabbitListener(queues = {"spring_topic_queue_star"})
    void test(Message message){
        System.out.println("消息接收成功：" + new String(message.getBody()));
    }
}
