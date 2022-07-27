package com.sheldon.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fangxiaodong
 * @date 2021/09/13
 */
public class RegexUtil {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^((1[3,5,8][0-9])|(14[5,7,9])|(17[0,1,3,5-8]))\\d{8}$");

    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]*");

    public static void main(String[] args) {
        Matcher matcher = MOBILE_PATTERN.matcher("13209130001");
        System.out.println(matcher.matches());

        Matcher matcher1 = CHINESE_PATTERN.matcher("你好1");
        System.out.println(matcher1.matches());
    }

}
