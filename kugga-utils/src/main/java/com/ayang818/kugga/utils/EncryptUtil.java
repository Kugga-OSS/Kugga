package com.ayang818.kugga.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/2/5 14:10
 **/
public class EncryptUtil {
    public static String encrypt(String password, String salt) {
        return new Md5Hash(password, salt).toString();
    }

    public static Boolean compare(String password, String salt, String comparedPassword) {
        System.out.println(new Md5Hash(password, salt).toString());
        System.out.println(comparedPassword);
        return new Md5Hash(password, salt).toString().equals(comparedPassword);
    }
}
