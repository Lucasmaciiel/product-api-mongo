package com.desafiojavaspringboot.uol.api.service;

import com.desafiojavaspringboot.uol.api.model.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${compasso.rabbitmq.exchange}")
    private String exchange;

    @Value("${compasso.rabbitmq.routingkey}")
    private String routingkey;

    public void send(Product product) {
        rabbitTemplate.convertAndSend(exchange, routingkey, product);
        System.out.println("Send msg = " + product);

    }
}
