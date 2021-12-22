package com.cyscm.springboot_01_rabbitmq_producer;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class Springboot01RabbitmqProducerApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static String TOPIC_EXCHANGE = "spring_topic_exchange";

    @Test
    void contextLoads() {
        assertFalse(false);
    }

    @Test
    void send(){
        assertFalse(false);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, "atguigu.hahah",
                "springboot rabbitmq===================");
    }
}
