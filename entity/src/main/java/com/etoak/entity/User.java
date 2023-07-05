package com.etoak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("uni_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private String userPic;
    private String userCover;
    private String sex;
    private String content;
    private Integer age;
    private String openid;

    private Integer role;
    private String email;
    private String phone;
    private Integer status;
}
