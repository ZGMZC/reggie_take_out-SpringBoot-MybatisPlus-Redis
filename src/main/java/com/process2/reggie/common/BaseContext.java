package com.process2.reggie.common;

/**
 * 基于ThreadLocal封装工具类
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    //设置当前线程id
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    //获取当前线程id
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
