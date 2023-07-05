package com.etoak.service;

import com.etoak.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserService {

    @GetMapping("/rst-user/user/getUser")
    ResultVO getUser(@RequestParam String name);

}
