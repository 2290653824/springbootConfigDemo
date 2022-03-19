package com.zj.rabbitmqspringboot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class TtlQueueConfig {

    public static final String X_EXCHANGE="X";
    public static final String QUEUE_A="QA";
    public static final String QUEUE_B="QB";
    public static final String QUEUE_C="QC";


    public static final String Y_DEAD_LETTER_EXCHANGE="Y";
    public static final String DEAD_LETTER_QUEUE="QD";
    public static final String DEAD_BINDING="YD";
    public static final String XANDQA_BINDING="XA";
    public static final String XANDQB_BINDING="XB";
    public static final String XANDQC_BINDING="XC";

    @Bean("getXDirectChange")
    public DirectExchange getXDirectChange(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("getYDirectExchange")
    public DirectExchange getYDirectExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    @Bean("getQAQueue")
    public Queue getQAQueue(){
        HashMap<String,Object> args = new HashMap<String,Object>();
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key",DEAD_BINDING);
        args.put("x-message-ttl",10000);
        return new Queue(QUEUE_A,false,false,false,args);
    }

    @Bean("getQBQueue")
    public Queue getQBQueue(){
        HashMap<String,Object> args = new HashMap<String,Object>();
        args.put("x-dead-letter-routing-key",DEAD_BINDING);
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        args.put("x-message-ttl",40000);
        return new Queue(QUEUE_B,false,false,false,args);
    }

    @Bean("getDQQueue")
    public Queue getDQQueue(){
        return new Queue(DEAD_LETTER_QUEUE,false,false,false,null);
    }

    @Bean
    public Binding queuebAindingX(@Qualifier("getQAQueue") Queue queue,@Qualifier("getXDirectChange") DirectExchange exchan){
        return BindingBuilder.bind(queue).to(exchan).with(XANDQA_BINDING);
    }

    @Bean
    public Binding queuebBindingX(@Qualifier("getQBQueue") Queue queue,@Qualifier("getXDirectChange") DirectExchange exchan){
        return BindingBuilder.bind(queue).to(exchan).with(XANDQB_BINDING);
    }

    @Bean
    public Binding queuedBindingD(@Qualifier("getDQQueue") Queue queue,@Qualifier("getYDirectExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_BINDING);
    }


    @Bean("getQueueC")
    public Queue getQueueC(){
        HashMap<String,Object> args = new HashMap<String,Object>();
        args.put("x-dead-letter-routing-key",DEAD_BINDING);
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        return new Queue(QUEUE_C,false,false,false,args);
    }

    @Bean
    public Binding queueCBindingX(@Qualifier("getXDirectChange") DirectExchange exchange ,@Qualifier("getQueueC") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(XANDQC_BINDING);
    }




}
