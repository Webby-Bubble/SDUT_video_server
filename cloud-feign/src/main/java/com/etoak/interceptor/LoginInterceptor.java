package com.etoak.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.etoak.service.UserService;
import com.etoak.utils.EncryptorUtils;
import com.etoak.utils.ErrorLoginException;
import com.etoak.vo.ResultVO;
import com.etoak.vo.TokenVo;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Feign登录拦截器
 * Author @冷月
 * Date 2023/4/3 15:18
 */
//@Component
@Slf4j
public class LoginInterceptor implements RequestInterceptor {
    @Autowired
    UserService userService;

    private static  final  String TOKEN = "token";


    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取HttpServletRequest对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String requestURI = request.getRequestURI();
        log.info("登录拦截器已执行====");

       /* if(StringUtils.containsAny(requestURI,"/user/login","/user/getCode")){
            *//* 验证请求头中有没有token *//*
            // 获取请求头信息
            String token = request.getHeader(TOKEN);
            if(token == null){
                log.info("没有token令牌");
                throw new ErrorLoginException("没有token令牌");
            }

            *//* 如果有token则判断用户信息是否正确 *//*
            String userStr = EncryptorUtils.decrypt(token);
            TokenVo tokenVo = JSONObject.parseObject(userStr, TokenVo.class);

            *//*校验token有效期*//*
            if(System.currentTimeMillis() - tokenVo.getExpire() > 0){
                log.info("token过期");
                throw new ErrorLoginException("token过期,请重新登录");
            }

            *//* 验证用户名 *//*
            ResultVO resultVO = userService.getUser(tokenVo.getName());
            if(ObjectUtils.isEmpty(resultVO.getData())){
                log.info("认证信息错误");
                throw new ErrorLoginException("没有用户信息，认证信息错误！");
            }

        }*/




    }
}
