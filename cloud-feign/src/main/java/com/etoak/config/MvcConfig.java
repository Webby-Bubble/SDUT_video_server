package com.etoak.config;

import com.etoak.filter.CrossDomainFilter;
import com.etoak.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录拦截器和静态资源加载配置
 * Author @冷月
 * Date 2023/4/3 15:16
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

//    @Autowired
    LoginInterceptor loginInterceptor;

   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/files/**");
    }*/
   //SpringBoot注册过滤器
  /* @Bean
   public FilterRegistrationBean<CrossDomainFilter> filterRegistrationBean(){
       FilterRegistrationBean<CrossDomainFilter> filter = new FilterRegistrationBean<>();
       filter.setFilter(new CrossDomainFilter());
       //设置过滤器的优先级 1表示优先级最高
       filter.setOrder(1);
       //设置请求的拦截方式  /* 表示拦截所有请求
       filter.addUrlPatterns("/*");
       return filter;
   }*/

}
