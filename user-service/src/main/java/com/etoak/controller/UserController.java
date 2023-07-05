package com.etoak.controller;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSONObject;
import com.etoak.commons.ImageVerificationCode;
import com.etoak.dto.UserDto;
import com.etoak.entity.DisableUser;
import com.etoak.entity.Page;
import com.etoak.entity.User;
import com.etoak.service.UserService;
import com.etoak.utils.EncryptorUtils;
import com.etoak.vo.ResultVO;
import com.etoak.vo.TokenVo;
import com.etoak.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public ResultVO login(UserVo user){
        //判断是用户密码登录还是手机号验证码登录
        UserDto user1 = new UserDto();
        if(StringUtils.isBlank(user.getPhone())){
             user1 = userService.getUser(user.getUserName());
            if(ObjectUtils.isEmpty(user1)){
                log.info("用户名错误");
                return ResultVO.error("用户名或密码错误！");
            }
            /* 2、比对密码，比对失败，则返回前端；如果比对成功，执行第3步 */
            String password =  MD5.create().digestHex(user.getPassword());
            if (!StringUtils.equals(password, user1.getPassword())) {
                log.info("密码错误");
                return ResultVO.error("用户名或密码错误！");
            }
        }else {
            //手机号登录
           user1 =  userService.getUserByPhone(user.getPhone());

            /* 判断验证码 */
            String redisCode = redisTemplate.opsForValue().get("login:code");
            if(StringUtils.isBlank(redisCode)){
                return ResultVO.error("验证码已过期！");
            }else if(!StringUtils.equalsIgnoreCase(user.getCode(),redisCode)) {
                return ResultVO.error("验证码错误！");
            }

        }

       TokenVo tokenVo = new TokenVo(user1.getUserName());
       String token = EncryptorUtils.encrypt(JSONObject.toJSONString(tokenVo));
       Map<String,Object> map = new HashMap<>();
       map.put("user",user1);
       map.put("token",token);
       return ResultVO.success(map);
    }

    @GetMapping("/getUser")
    public ResultVO getUser(@RequestParam String name){
       User user =  userService.getUser(name);
       if(user != null)return ResultVO.success(user);
       return ResultVO.error();
    }

    @PostMapping("/updateUserInfo")
    public ResultVO updateUserInfo(@RequestBody User user){
        int i =  userService.updateUserInfo(user);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }


    /** 获取验证码 */
    @GetMapping("/getCode")
    public ResultVO getCode(){
        ImageVerificationCode imageVerificationCode = new ImageVerificationCode();
        BufferedImage image = imageVerificationCode.getImage();
        //将验证码的内容存储到redis数据库设置验证码的有效期为1分钟
        redisTemplate.opsForValue().set("login:code",imageVerificationCode.getText());
        redisTemplate.expire("login:code",1, TimeUnit.MINUTES);
        Map<String,String> map = new HashMap<>();
        //封装验证码图片访问路径
        try {
            //创建ByteArrayOutputStream对象
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(image,"JPEG",outputStream);

            // Base64编码器将字节数组编码为Base64格式的字符串
            byte[] bytes = outputStream.toByteArray();
            String suffix = Base64.getEncoder().encodeToString(bytes);

            //定义base64前缀格式
            String prefix = "data:image/jpeg;base64,";
            String baseImageUrl = prefix  + suffix;

            //封装返回数据
            map.put("code",imageVerificationCode.getText());
            map.put("baseImageUrl",baseImageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return ResultVO.success(map);
    }

    @PostMapping("/updateUserPicOrUserCover")
    public ResultVO updateUserPicOrUserCover( @RequestBody User user){
        int i =  userService.updateUserPicOrUserCover(user);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }


    /*查询关注 获赞 粉丝数量*/
    @RequestMapping("/querySomeCountOfRedis")
    public ResultVO querySomeCountOfRedis(Integer userId){
        UserDto userDto =  userService.querySomeCountOfRedis(userId);
        return ResultVO.success(userDto);
    }

    /*查询某个用户的所有关注或粉丝*/
    @RequestMapping("/queryAllGuanZhuByUserId")
    public ResultVO queryAllGuanZhuByUserId(Integer userId,Integer type,String userName){
        List<UserDto> userList =  userService.queryAllGuanZhuByUserId(userId,type,userName);
        return ResultVO.success(userList);
    }

    /* 查询所有用户列表 */
    @GetMapping("/getAllUserList")
    public ResultVO getAllUserList(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                   @RequestParam(required = false,defaultValue = "4")Integer pageSize,
                                   User user){
        Page<UserDto> userDtoPage = userService.getAllUserList(pageNum,pageSize,user);
        return ResultVO.success(userDtoPage);
    }

    /*用户封禁*/
    @PostMapping("/disableUser")
    public ResultVO disableUser(@RequestBody DisableUser disableUser){
       int i =  userService.disableUser(disableUser);
       if(i > 0)return ResultVO.success();
       return ResultVO.error();
    }

    /*解封用户*/
    @GetMapping("/updateUserStatus")
    public ResultVO updateUserStatus(@RequestParam Integer userId,@RequestParam Integer status){
        int i =  userService.updateUserStatus(userId,status);

        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }

    /*获取所有用户个数*/
    @GetMapping("/getAllUserCount")
    public ResultVO getAllUserCount(){
        int count = userService.getAllUserCount();
        return ResultVO.success(count);
    }
}
