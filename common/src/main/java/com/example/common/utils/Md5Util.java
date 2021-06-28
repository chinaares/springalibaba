package com.example.common.utils;

import org.springframework.util.DigestUtils;

/**
 * md5加密解密工具类
 */
public class Md5Util {

    //加密
    public static String encode(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    //匹配是否正确
    public static boolean isMatch(String ostr,String nstr) {
        if (encode(ostr).equals(nstr)) {
            return true;
        } else {
            return false;
        }
    }
}