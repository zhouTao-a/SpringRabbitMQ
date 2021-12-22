package com.cyscm.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-rabbitmq-producer.xml"})
public class SpringRabbitTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 只发队列消息
     * 默认交换机类型为 direct
     * 交换机的名称为空,路由键为队列的名称
     */
    @Test
    public void queueTest(){
        assertFalse(false);

        //发送消息,路由键与队列同名
        rabbitTemplate.convertAndSend("spring_queue", "只发队列spring_queue的消息。");
    }

    /**
     * 只发队列消息
     * 默认交换机类型为 direct
     * 交换机的名称为空,路由键为队列的名称
     */
    @Test
    public void queueConfirmTest(){
        assertFalse(false);
        /**
         * 确认模式：
         *     步骤：
         *      1：确认模式开启
         *      2：在rabbitTemplate定义confirmCallback回调函数
         */
        //定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param b exchange交换机，是否成功收到了消息。true成功 false失败
             * @param s 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("回调函数执行成功");
                if (b) {
                    System.out.println("消息发送成功");
                }else {
                    System.out.println("消息发送发送失败,原因：" + s);
                }
            }
        });

        //发送消息,路由键与队列同名
        rabbitTemplate.convertAndSend("spring_queue", "只发队列spring_queue的消息。");
    }

    /**
     * 只发队列消息
     * 默认交换机类型为 direct
     * 交换机的名称为空,路由键为队列的名称
     */
    @Test
    public void queueReturnTest(){
        assertFalse(false);
        /**
         * 确认模式：
         *     步骤：
         *      1：return模式开启 publisher-returns="true"
         *      2：在rabbitTemplate定义confirmCallback回调函数
         */
        rabbitTemplate.setMandatory(true);
        //定义回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("退回模式执行了" + returnedMessage.getMessage());
            }
        });

        //发送消息,路由键与队列同名
        rabbitTemplate.convertAndSend("spring_queue", "只发队列spring_queue的消息。");
    }

    /**
     * 发送广播
     * 交换机类型为 fanout
     * 绑定到该交换机的所有队列都能够收到消息
     */
    @Test
    public void fanoutReturnTest(){
        assertFalse(false);
        /**
         * 确认模式：
         *     步骤：
         *      1：return模式开启 publisher-returns="true"
         *      2：在rabbitTemplate定义confirmCallback回调函数
         */
        //定义回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("退回模式执行了" + returnedMessage.getMessage());
            }
        });
        /**
         * 参数1：交换机名称
         * 参数2：路由键名（广播设置为空）
         * 参数3：发送的消息内容
         */
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "发送到spring_fanout_exchange交换机的广播消息");
    }

    /**
     * 发送广播
     * 交换机类型为 fanout
     * 绑定到该交换机的所有队列都能够收到消息
     */
    @Test
    public void fanoutTest(){
        testDriver();
        /**
         * 参数1：交换机名称
         * 参数2：路由键名（广播设置为空）
         * 参数3：发送的消息内容
         */
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "发送到spring_fanout_exchange交换机的广播消息");
    }

    /**
     * 通配符
     * 交换机类型为 topic
     * 匹配路由键的通配符,*表示一个单词,#表示多个单词
     * 绑定到该交换机的匹配队列能够收到对应消息
     */
    @Test
    public void topicTest(){
        testDriver();
        /**
         * 参数1：交换机名称
         * 参数2：路由键名
         * 参数3：发送的消息内容
         */
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj", "发送到spring_topic_exchange交换机atguigu.bj的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj.1", "发送到spring_topic_exchange交换机atguigu.bj.1的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj.2", "发送到spring_topic_exchange交换机atguigu.bj.2的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "guigu.cn", "发送到spring_topic_exchange交换机guigu.cn的消息");
    }

    @Test
    public void testDriver(){
        assertFalse(false);
    }

}
