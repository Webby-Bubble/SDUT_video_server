package com.etoak.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.etoak.config.AutowiredBean;
import com.etoak.service.UserService;
import com.etoak.utils.EncryptorUtils;
import com.etoak.utils.ErrorLoginException;
import com.etoak.vo.ResultVO;
import com.etoak.vo.TokenVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Gateway全局过滤器
 * Author @冷月
 * Date 2023/4/26 14:17
 */
//@Component
@Slf4j
public class GatewayFilter implements GlobalFilter , Ordered {


    private final String TOKEN = "token";
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //要放行的请求列表
        ArrayList<String> excludePath = new ArrayList<>();
        Collections.addAll(excludePath,"/rst-user/user/login","/rst-user/user/register","/rst-user/user/getCode");

        //判断请求是否需要校验
        RequestPath requestPath = exchange.getRequest().getPath();
        if(!excludePath.contains(requestPath.toString())){
            //需要校验请求信息
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String headerToken = headers.getFirst(TOKEN);

            //判断token是否为空
            if(headerToken == null){
                log.info("没有token令牌");
                throw new ErrorLoginException("没有token令牌");
            }

            //如果有token则判断用户信息是否正确
            String userStr = EncryptorUtils.decrypt(headerToken);
            TokenVo tokenVo = JSONObject.parseObject(userStr, TokenVo.class);

            //校验token有效期
            if(System.currentTimeMillis() - tokenVo.getExpire() > 0){
                log.info("token过期");
                throw new ErrorLoginException("token过期,请重新登录");
            }


             //获取的关键看这里，在用的时候在获取bean
             UserService userService = AutowiredBean.getBean(UserService.class,exchange.getApplicationContext());

             //异步调用feign服务接口 　　
             CompletableFuture<ResultVO> resultVo = CompletableFuture.supplyAsync(()->{
                 return userService.getUser(tokenVo.getName());
             });

            if(resultVo.get().getCode() != 200){
                log.info("认证信息错误");
                throw new ErrorLoginException("没有用户信息，认证信息错误！");
            }
            //放行
            return chain.filter(exchange);
        }else {
            //放行
            return chain.filter(exchange);
        }

    }

    @Override
    public int getOrder() {
        return -800;
    }
}
