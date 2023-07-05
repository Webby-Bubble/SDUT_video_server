package com.etoak.controller.advice;

import com.etoak.utils.ErrorLoginException;
import com.etoak.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理器
 * Author @冷月
 * Date 2023/4/3 16:38
 */
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ErrorLoginException.class)
    public ResultVO loginError(ErrorLoginException e){
        return ResultVO.noAuth(e.getMessage());
    }

}
