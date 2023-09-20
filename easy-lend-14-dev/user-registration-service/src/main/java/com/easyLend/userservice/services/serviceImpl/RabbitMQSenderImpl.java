package com.easyLend.userservice.services.serviceImpl;

import com.easyLend.userservice.response.UserResponse;
import com.easyLend.userservice.services.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RabbitMQSenderImpl implements RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;
    @Value("${rabbitmq.routingkey}")
    private String ROUTING_KEY;
    @Value("${rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    private static Logger logger = LogManager.getLogger(RabbitMQSenderImpl.class.toString());


    public void send(UserResponse userResponse) {
        System.out.println(ROUTING_KEY);
        System.out.println(EXCHANGE_NAME);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY, userResponse);
        logger.info("Sending Message to the Queue : " + userResponse.toString());
    }
}
