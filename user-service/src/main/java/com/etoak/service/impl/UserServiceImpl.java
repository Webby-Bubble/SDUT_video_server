package com.etoak.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.etoak.dto.UserDto;
import com.etoak.entity.DisableUser;
import com.etoak.entity.Page;
import com.etoak.entity.User;
import com.etoak.mapper.UserMapper;
import com.etoak.mqConfig.EnableUserRabbitMq;
import com.etoak.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Resource
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public User login(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name",user.getUserName());
        qw.eq("password",user.getPassword());
        User user1 = userMapper.selectOne(qw);
        return user1;
    }

    @Override
    public UserDto getUser(String name) {
        return userMapper.getUser(name);
    }

    @Override
    public UserDto getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }
    @Override
    public int updateUserPicOrUserCover(User user) {
        return userMapper.updateUserPicOrUserCover(user);
    }
    @Override
    public UserDto querySomeCountOfRedis(Integer userId) {
        UserDto u = new UserDto();
        //封装用的关注数量
        u.setGuanZhuCount(redisTemplate.opsForSet().size("guanzhu"+userId));
        //封装粉丝数量
        u.setFenSiCount(redisTemplate.opsForSet().size("fensi"+userId));
        //封装视频点赞量
        List<Integer> shortVideoIdList  = userMapper.getAllShortVideoByUserId(userId);
        long shortVideoLikeCount = 0;
        for(Integer id : shortVideoIdList){
            shortVideoLikeCount +=  redisTemplate.opsForSet().size("shortVideo"+id);
        }
        u.setShortVideoLikeCount(shortVideoLikeCount);
        return u;
    }

    @Override
    public List<UserDto> queryAllGuanZhuByUserId(Integer userId,Integer type,String userName) {
        if(type == 1){//查询关注列表
            String key = "guanzhu" + userId;
            Set<String> guanzhuIds = redisTemplate.opsForSet().members(key);
            if(guanzhuIds.size() != 0){
                Set<Integer> idSet = guanzhuIds.stream().map(Integer::parseInt).collect(Collectors.toSet());
                List<UserDto> userList =  userMapper.queryAllUsersByIds(idSet,userName);
                return userList;
            }else{
                return new ArrayList<>();
            }
        }else{
            String key = "fensi" + userId;
            Set<String> fenSiIds = redisTemplate.opsForSet().members(key);
            if(fenSiIds.size() != 0){
                Set<Integer> idSet = fenSiIds.stream().map(Integer::parseInt).collect(Collectors.toSet());
                List<UserDto> userList =  userMapper.queryAllUsersByIds(idSet,userName);
                for(UserDto uu :userList){
                    /*判断是否相互关注*/
                    if(redisTemplate.opsForSet().isMember("guanzhu"+userId,uu.getId()+"")){
                        uu.setMutual(true);
                    }
                }
                return userList;
            }else{
                return new ArrayList<>();
            }

        }
    }

    /* 查询所有用户 */
    @Override
    public Page<UserDto> getAllUserList(Integer pageNum,Integer pageSize,User user) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserDto> userDtos =  userMapper.getAllUserList(user);
        userDtos.forEach(u->{
            u.setGuanZhuCount(redisTemplate.opsForSet().size("guanzhu"+u.getId()));
            //封装粉丝数量
            u.setFenSiCount(redisTemplate.opsForSet().size("fensi"+u.getId()));
        });
        PageInfo<UserDto> pageInfo = new PageInfo<>(userDtos);
        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getList(),
                pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());
    }

    @Transactional
    @Override
    public int disableUser(DisableUser disableUser) {
        int x =  userMapper.updateUserStatus(disableUser.getUserId(),2);
        if(x > 0){
            userMapper.disableUser(disableUser);
            rabbitTemplate.convertAndSend(EnableUserRabbitMq.ENABLE_EXCHANGE,
                    EnableUserRabbitMq.ENABLE_KEY,disableUser.getUserId(),message -> {
                        //设置延迟时间
                        int dayTime = 24 * 3600000;
                        message.getMessageProperties().setDelay(disableUser.getAllTime() * dayTime);
                        return message;
                    });

        }
        return x;
    }

    @Transactional
    @Override
    public int updateUserStatus(Integer userId, Integer status) {
        int x =  userMapper.updateUserStatus(userId,status);
        if(x > 0){
            userMapper.deleteUserDisable(userId);

        }
        return x;
    }

    @Override
    public int getAllUserCount() {
        return userMapper.getAllUserCount();
    }
}
