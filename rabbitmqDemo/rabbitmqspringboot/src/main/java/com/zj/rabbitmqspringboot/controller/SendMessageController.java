package com.zj.rabbitmqspringboot.controller;

import com.zj.rabbitmqspringboot.config.TtlQueueConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ttl")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/{message}")
    public void sentMsg(@PathVariable("message") String message){
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGE,TtlQueueConfig.XANDQA_BINDING,"来自ttl为10s的队列的信息："+message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGE,TtlQueueConfig.XANDQB_BINDING,"来自ttl为40s的队列的信息："+message);


    }

    @RequestMapping("/{msg}/{time}")
    public void sent(@PathVariable("msg") String msg,@PathVariable("time") String time){
        String message="延时时间为："+time+"  信息为："+msg;
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGE,TtlQueueConfig.XANDQC_BINDING,message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message s) throws AmqpException {
                s.getMessageProperties().setExpiration(time);
                return s;
            }
        });
        System.out.println("发送消息:"+message);
    }
}
