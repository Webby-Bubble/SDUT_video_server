package com.etoak.mqConfig;

import com.etoak.service.UserService;
import com.etoak.vo.ResultVO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnableUserConsume {
    @Autowired
    UserService userService;

    @RabbitListener(queues = EnableUserRabbitMq.ENABLE_QUEUE)
    public void enableUser(String message){
        int userId = Integer.parseInt(message);
        userService.updateUserStatus(userId,1);

    }
}
