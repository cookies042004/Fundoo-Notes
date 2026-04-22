package com.fundoonotes.consumer;

import com.fundoonotes.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(String message) {

        System.out.println("Message Received: " + message);

        // Simulate Email Notification
        System.out.println("📧 Email sent for: " + message);
    }
}
