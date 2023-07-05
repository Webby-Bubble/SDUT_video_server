package com.etoak.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

  private int code;//状态码
  private String msg;//状态信息
  private T data;//用来装返回的数据

  private static final int SUCCESS_CODE = 200;
  private static final int ERROR_CODE = 201;
  private static final String SUCCESS_MSG = "success";
  private static final String ERROR_MSG = "error";

  public ResultVO(int code,String msg){
    this.code = code;
    this.msg = msg;
  }

  public static <T> ResultVO<T> success(T data){
    return new ResultVO<>(SUCCESS_CODE,SUCCESS_MSG,data);
  }
  public static <T> ResultVO<T> error(T data){
    return new ResultVO<>(ERROR_CODE,ERROR_MSG,data);
  }

  public static <T> ResultVO<T> success(){
    return new ResultVO<>(SUCCESS_CODE,SUCCESS_MSG);
  }
  public static <T> ResultVO<T> error(){
    return new ResultVO<>(ERROR_CODE,ERROR_MSG);
  }

  /* 无权访问 */
  public static <T> ResultVO<T> noAuth(T msg){
    return new ResultVO<>(403,ERROR_MSG,msg);
  }

}
