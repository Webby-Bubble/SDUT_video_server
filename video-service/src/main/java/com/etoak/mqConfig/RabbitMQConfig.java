package com.etoak.mqConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
/**
 * RabbitMq配置类
 * Author @冷月
 * Date 2023/4/12 15:02
 */
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "email_exchange";

    public static final String QUEUE = "email_queue";

    public static final String KEY = "email_key";

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(emailExchange())
                .with(KEY);
    }

}

