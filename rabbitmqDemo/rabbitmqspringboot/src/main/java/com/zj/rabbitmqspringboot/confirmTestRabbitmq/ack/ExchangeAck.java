package com.zj.rabbitmqspringboot.confirmTestRabbitmq.ack;

import com.zj.rabbitmqspringboot.confirmTestRabbitmq.config.RabbitmqConfig;
import org.apache.commons.logging.Log;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExchangeAck implements RabbitTemplate.ConfirmCallback , RabbitTemplate.ReturnCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();

        if(ack){
            System.out.print("交换机成功接收到信息：");

            System.out.println("id为："+id+" 信息接收成功");

        }else{
            System.out.println("没有成功接收到id为" +id+"的信息，原因为："+cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.print("队列宕机了，没有成功接收到消息：");
        System.out.println("消息为："+new String(message.getBody())+" exchange:"+exchange+" routing key:"+routingKey);

    }
}
