package com.zj.rabbitmqspringboot.rabbitListener;

import com.rabbitmq.client.Channel;
import com.zj.rabbitmqspringboot.config.TtlQueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Lisener {

    @RabbitListener(queues = "QD")
    public void receiveD(String mes){

        System.out.println("接收到了信息："+mes);
    }
}
