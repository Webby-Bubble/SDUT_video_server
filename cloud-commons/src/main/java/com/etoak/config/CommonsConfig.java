package com.etoak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CommonsConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*大白服务器文件地址
         * /usr/local/nginx/html/gsfile
         *   */
        //SpringBoot配置静态资源访问规则  当访问/pics/123.jpg  ----> D:/upload/shanke/img/ 下面去找相应的文件
        registry.addResourceHandler("/pics/**").addResourceLocations("file:D:/upload/uniapp/img/");
        registry.addResourceHandler("/videos/**").addResourceLocations("file:D:/upload/uniapp/video/");

    }
}
