package com.etoak.service;

import com.etoak.vo.ResultVO;
import com.etoak.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "user-service")
public interface UserService {

    @GetMapping("/rst-user/user/login")
    ResultVO login(@RequestParam String userName,@RequestParam String password );

    @GetMapping("/rst-user/user/getUser")
    ResultVO getUser(@RequestParam String name);

    @GetMapping("/rst-user/user/getCode")
    ResultVO getCode();

}
