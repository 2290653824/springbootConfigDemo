package com.zj.rabbitmqspringboot.confirmTestRabbitmq.Lisener;

import com.zj.rabbitmqspringboot.confirmTestRabbitmq.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComsumerLisener {

    @RabbitListener(queues = RabbitmqConfig.QUEUE)
    public void recieve(String message , CorrelationData correlationData){
        System.out.println("接收到id为："+correlationData.getId()+"的信息："+message);
    }

}
