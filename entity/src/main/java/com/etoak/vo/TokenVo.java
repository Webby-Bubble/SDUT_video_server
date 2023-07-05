package com.etoak.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token令牌实体类
 * Author @冷月
 * Date 2023/4/3 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo {
    private String name;
    private long expire;

    public TokenVo(String name){
        this.name = name;

        //过期时间 5分钟
        this.expire = System.currentTimeMillis()  +  60 * 1000 * 5;
    }
}
