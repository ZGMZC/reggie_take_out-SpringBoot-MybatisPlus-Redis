package com.process2.reggie.common;

/**
 * 自定义业务异常类
 */
public class CustomException extends RuntimeException{

    public CustomException(String message){
        super(message);
    }
}
