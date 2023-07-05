package com.etoak.controller;

import com.etoak.entity.User;
import com.etoak.service.UserService;
import com.etoak.vo.ResultVO;
import com.etoak.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ResultVO login(String name,String password){
        return  userService.login(name,password);
    }

    @GetMapping("/getCode")
    public ResultVO getCode(){
        return userService.getCode();
    }
}

