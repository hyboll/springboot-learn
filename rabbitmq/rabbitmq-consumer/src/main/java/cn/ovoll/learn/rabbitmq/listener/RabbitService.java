package cn.ovoll.learn.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitService {

    private static final String FANOUT_QUEUE = "springboot_fanout_queue";
    private static final String DIRECT_QUEUE = "springboot_direct_queue";
    private static final String TOPIC_QUEUE = "springboot_topic_queue";
    private static final String DEAD_QUEUE = "springboot_dead_queue";
    private static final String ORDER_QUEUE = "springboot_order_dlx_queue";
    private static final String DELAYED_QUEUE = "delayed.queue";

    @RabbitListener(queues = FANOUT_QUEUE)
    public void demo(Message msg) throws Exception {
        // 消费者操作
        System.out.println("收到消息："+ msg);
    }

    @RabbitListener(queues = DIRECT_QUEUE)
    @RabbitHandler
    public void receiver(@Payload String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            // 消费者操作
            System.out.println("收到消息："+ msg);
            // 手动签收消息
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            // 第三个参数表示是否重回队列
            channel.basicNack(deliveryTag, true, true);
        }
    }

    /**
     * 监听通配符队列
     * @param msg
     * @param deliveryTag
     * @param channel
     * @throws Exception
     */
    @RabbitListener(queues = TOPIC_QUEUE)
    @RabbitHandler
    public void topicQueue(@Payload String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            // 消费者操作
            System.out.println("收到消息："+ msg);
            // 手动签收消息
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            // 第三个参数表示是否重回队列
            channel.basicNack(deliveryTag, true, false);
        }
    }

    /**
     * 监听死信队列
     * @param msg
     * @param deliveryTag
     * @param channel
     * @throws Exception
     */
    @RabbitListener(queues = DEAD_QUEUE)
    @RabbitHandler
    public void dlxQueue(@Payload String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            // 消费者操作
            System.out.println("收到消息："+ msg);
            // 手动签收消息
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            // 第三个参数表示是否重回队列
            channel.basicNack(deliveryTag, true, true);
        }
    }

    /**
     * 监听延迟队列
     * @param msg
     * @param deliveryTag
     * @param channel
     * @throws Exception
     */
    @RabbitListener(queues = ORDER_QUEUE)
    @RabbitHandler
    public void orderQueue(@Payload String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            // 消费者操作
            System.out.println("收到消息："+ msg);
            // 手动签收消息
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            // 第三个参数表示是否重回队列
            channel.basicNack(deliveryTag, true, true);
        }
    }

    /**
     * 监听插件延迟队列
     */
    @RabbitListener(queues = DELAYED_QUEUE)
    @RabbitHandler
    public void delayedQueue(@Payload String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            // 消费者操作
            System.out.println("收到消息："+ msg);
            // 手动签收消息
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            // 第三个参数表示是否重回队列
            channel.basicNack(deliveryTag, true, true);
        }
    }
}
