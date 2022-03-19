package com.zj.rabbitmqspringboot.confirmTestRabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitmqConfig {

    public static final String QUEUE="confirm.queue";
    public static final String EXCHANGE="confirm.exchange";
    public static final String BINGING="key1";

    @Bean("getQueue")
    public Queue getQueue(){
        return new Queue(QUEUE,false,false,false,null);

    }

    @Bean("getExchange")
    public DirectExchange getExchange(){
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding getBinding(@Qualifier("getQueue") Queue queue,@Qualifier("getExchange") DirectExchange exchange ){
        return BindingBuilder.bind(queue).to(exchange).with(BINGING);
    }




}
