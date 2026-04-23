package com.fundoonotes.service.impl;

import com.fundoonotes.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsProducer {

    private final JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(JmsConfig.QUEUE, message);
        System.out.println("JMS Message Sent: " + message);
    }
}