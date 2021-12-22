package com.cyscm.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 1、声明队列
 * 2、创建连接
 * 3、创建通道
 * 4、通道声明队列
 * 5、重写消息消费方法
 * 6、执行消息方法
 * @author zhoutao
 */
public class Consumer {

    private static final String QUEUE ="queue";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            //mq服务ip地址
            connectionFactory.setHost("1.12.248.64");
            //mq client连接端口
            connectionFactory.setPort(5672);
            //mq登录用户名
            connectionFactory.setUsername("admin");
            //mq登录密码
            connectionFactory.setPassword("qaz123wsx");
            //rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器
            connectionFactory.setVirtualHost("my_vhost");
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            //通道绑定队列
            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * param1:队列名称
             * param2:是否持久化
             * param3:队列是否独占此连接
             * param4:队列不再使用时是否自动删除此队列
             * param5:队列参数
             * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             *
             */
            //通道绑定邮件队列
            channel.queueDeclare(QUEUE,true,false,false,null);

            //String consumerTag, Envelope envelope, BasicProperties properties, byte[] body
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                /**
                 * 消费者接收消息调用此方法
                 * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
                 * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志
                (收到消息失败后是否需要重新发送)
                 * @param properties
                 * @param body
                 * @throws IOException
                 * String consumerTag, Envelope envelope, BasicProperties properties, byte[] body
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //交换机
                    String exchange = envelope.getExchange();
                    //路由key
                    String routingKey = envelope.getRoutingKey();
                    envelope.getDeliveryTag();
                    String msg = new String(body,"utf-8");
                    System.out.println("mq收到的消息是："+msg );
                }

            };
            System.out.println("消费者启动成功！");
            //执行消息方法
            channel.basicConsume(QUEUE,true, consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
