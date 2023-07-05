package com.etoak.utils;

import com.alibaba.fastjson.JSONObject;
import com.etoak.vo.TokenVo;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * 加解密工具类
 * Author @冷月
 * Date 2023/4/3 14:46
 */
public class EncryptorUtils {
    private static final StandardPBEStringEncryptor ENCRYPTOR =
            new StandardPBEStringEncryptor();

    static{
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();

        //加密算法
        config.setAlgorithm("PBEWithMD5AndDES");

        // 口令 不是秘钥
        config.setPassword("etoak");
        ENCRYPTOR.setConfig(config);
    }


    /* 对密文解密 */
    public static String decrypt(String msg){
        return ENCRYPTOR.decrypt(msg);
    }

    /* 对明文加密 */
    public static String encrypt(String msg){
        return ENCRYPTOR.encrypt(msg);
    }

    public static void main(String[] args) {
        TokenVo tokenVo = new TokenVo("冷月");
        System.out.println(encrypt(JSONObject.toJSONString(tokenVo)));
    }

}
