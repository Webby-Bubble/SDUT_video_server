package com.etoak.mqConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动解封用户消息队列配置
 * Author @冷月
 * Date 2023/4/27 15:01
 */
@Configuration
public class EnableUserRabbitMq {
    /*解封交换机*/
    public static final String ENABLE_EXCHANGE = "enable_exchange";

    /*解封队列*/
    public static final String ENABLE_QUEUE = "enable_queue";

    /*解封key*/
    public static final String ENABLE_KEY = "enable_key";

    @Bean
    public CustomExchange enableExchange(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange(ENABLE_EXCHANGE,"x-delayed-message",true,false,args);
    }

    @Bean
    public Queue enableQueue(){
        return new Queue(ENABLE_QUEUE);
    }

    @Bean
    public Binding enableBinding(){
        return BindingBuilder.bind(enableQueue()).to(enableExchange()).with(ENABLE_KEY).noargs();
    }
}
