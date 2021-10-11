package com.example.interview;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-08-24 16:24:31
 */
public class StringPool {
    public static void main(String[] args) {
        String str = new String("swswswswsc");
        System.out.println(str);
        System.out.println(str.intern());
        System.out.println(str == str.intern());

        String str3 = new StringBuffer("swswswswsc").append("sdssdsd").toString();
        System.out.println(str3);
        System.out.println(str3.intern());
        System.out.println(str3 == str3.intern());

        String str2 = new StringBuffer("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        System.out.println(str2 == str2.intern());
        
    }
}
