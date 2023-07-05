package com.etoak.service;

import com.etoak.dto.UserDto;
import com.etoak.entity.DisableUser;
import com.etoak.entity.Page;
import com.etoak.entity.User;

import java.util.List;

public interface UserService  {
    User login(User user);

    UserDto getUser(String name);

    UserDto getUserByPhone(String phone);

    int updateUserInfo(User user);

    int updateUserPicOrUserCover(User user);

    UserDto querySomeCountOfRedis(Integer userId);

    List<UserDto> queryAllGuanZhuByUserId(Integer userId, Integer type, String userName);

    Page<UserDto> getAllUserList(Integer pageNum,Integer pageSize,User user);

    int disableUser(DisableUser disableUser);

    int updateUserStatus(Integer userId, Integer status);

    int getAllUserCount();


}
