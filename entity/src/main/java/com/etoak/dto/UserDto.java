package com.etoak.dto;

import com.etoak.entity.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private long guanZhuCount;//关注的数量
    private long fenSiCount;//粉丝数量
    private long shortVideoLikeCount;//短视频的获赞量
    private boolean mutual;//是否相互关注
    private String disableStartTime;//封禁起始时间
    private Integer disableAllTime;//封禁时长
    private String disableEndTime;//解封时间
}
