package com.etoak.vo;

import com.etoak.entity.User;
import lombok.Data;

@Data
public class UserVo extends User {
    private String code;
}
