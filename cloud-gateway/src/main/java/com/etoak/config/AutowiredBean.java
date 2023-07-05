package com.etoak.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 获取feign服务类AutowiredBean
 * Author @冷月
 * Date 2023/4/26 15:36
 */

public class AutowiredBean{

    private static ApplicationContext applicationContext;

     public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (AutowiredBean.applicationContext == null) {
            AutowiredBean.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz,ApplicationContext applicationContext) {
        AutowiredBean.applicationContext = applicationContext;
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}