package com.fundoonotes.consumer;

import com.fundoonotes.config.JmsConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer {

    @JmsListener(destination = JmsConfig.QUEUE)
    public void consume(String message) {
        System.out.println("JMS Message Received: " + message);
    }
}