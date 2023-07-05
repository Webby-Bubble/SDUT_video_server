package com.etoak.advice;

import com.alibaba.fastjson.JSONObject;
import com.etoak.utils.ErrorLoginException;
import com.etoak.vo.ResultVO;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

/**
 * gateway全局异常处理器
 * Author @冷月
 * Date 2023/4/3 16:38
 */
@Component
public class GatewayErrorController implements ErrorWebExceptionHandler {


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        ResultVO resultVO = new ResultVO();
        if (ex instanceof ErrorLoginException) {
            resultVO.setCode(520);
            resultVO.setData("权限错误：" + ex.getMessage());
        }else{
            resultVO.setCode(520);
            resultVO.setData("系统异常");
        }
        DataBuffer dataBuffer = response.bufferFactory()
                .allocateBuffer().write(JSONObject.toJSONString(resultVO).getBytes());

        //基于流形式
        response.getHeaders().setContentType(MediaType.APPLICATION_NDJSON);
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(dataBuffer)));
    }
}
