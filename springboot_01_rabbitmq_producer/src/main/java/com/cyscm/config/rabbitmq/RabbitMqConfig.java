package com.cyscm.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoutao
 */
@Configuration
public class RabbitMqConfig {

    private static String TOPIC_EXCHANGE = "spring_topic_exchange";
    private static String TOPIC_QUEUE = "spring_topic_queue_star";

    /**
     * 1: 交换机
     * 2：队列
     * 3：绑定交换机和队列
     */
    @Bean("bootExchange")
    public Exchange bootExchange(){
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).build();
    }

    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(TOPIC_QUEUE).build();
    }

    @Bean("bootBinding")
    public Binding bootBinding(@Qualifier("bootExchange") Exchange exchange, @Qualifier("bootQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("atguigu.*").noargs();
    }
}
