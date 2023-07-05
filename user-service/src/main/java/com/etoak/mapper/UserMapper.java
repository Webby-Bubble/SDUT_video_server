package com.etoak.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.etoak.dto.UserDto;
import com.etoak.entity.DisableUser;
import com.etoak.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper extends BaseMapper<User> {
    UserDto getUserByPhone(String phone);

    int updateUserInfo(User user);

    int updateUserPicOrUserCover(User user);

    List<Integer> getAllShortVideoByUserId(Integer userId);

    List<UserDto> queryAllUsersByIds(@Param("idList")Set<Integer> idSet, @Param("userName")String userName);

    List<UserDto> getAllUserList(User user);

    int disableUser(DisableUser disableUser);

    int updateUserStatus(@Param("userId") int userId, @Param("status") int i);

    void deleteUserDisable(Integer userId);

    UserDto getUser(String name);

    int getAllUserCount();

}
