package com.zj.rabbitmqspringboot.confirmTestRabbitmq.controller;

import com.zj.rabbitmqspringboot.confirmTestRabbitmq.ack.ExchangeAck;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/confirm")
public class PublisherController {

    public static final String QUEUE="confirm.queue";
    public static final String EXCHANGE="confirm.exchange";
    public static final String BINGING="key1";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ExchangeAck exchangeAck;




    @PostConstruct
    public void init(){
     rabbitTemplate.setConfirmCallback(exchangeAck);
//     rabbitTemplate.setMandatory(true);
     rabbitTemplate.setReturnCallback(exchangeAck);
    }

    @RequestMapping("/{message}")
    public void send(@PathVariable String message){
        CorrelationData correlationData = new CorrelationData("1");
        System.out.println("将要发送"+correlationData.getId()+"信息:"+message);
        rabbitTemplate.convertAndSend(EXCHANGE,BINGING,message,correlationData);

        CorrelationData correlationData1 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(EXCHANGE,BINGING+"2",message+"2",correlationData1);

    }
}
