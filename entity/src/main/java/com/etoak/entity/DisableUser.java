package com.etoak.entity;

import lombok.Data;

/**
 * 用户封禁
 * Author @冷月
 * Date 2023/4/27 13:52
 */
@Data
public class DisableUser {
    private int id;
    private int userId;
    private String startTime;
    private Integer allTime;
    private String endTime;
}
