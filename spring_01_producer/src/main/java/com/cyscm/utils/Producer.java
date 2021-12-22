package com.cyscm.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 1、声明队列
 * 2、创建连接
 * 3、创建通道
 * 4、通道声明队列
 * 5、制定消息
 * 6、发送消息，使用默认交换机
 * @author zhoutao
 */
public class Producer {
    /**
     * 声明队列
     */
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
            //创建与RabbitMQ服务的TCP连接
            connection = connectionFactory.newConnection();
            //创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
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

            int count = 10;
            for(int i = 0; i < count; i++){
                String message = new String(i + "mq 发送消息。。。");
                /**
                 * 消息发布方法
                 * exchange：Exchange的名称，如果没有指定，则使用Default Exchange
                 * routingKey:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
                 * props:消息包含的属性
                 * body：消息体
                 * 这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，但是不能显示绑定或解除绑定
                 * 默认的交换机，routingKey等于队列名称
                 */
                channel.basicPublish("", QUEUE,null, message.getBytes("utf-8"));
                System.out.println("mq消息发送成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
