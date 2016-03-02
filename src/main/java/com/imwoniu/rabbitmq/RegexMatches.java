package com.imwoniu.rabbitmq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Work on 2016/3/2.
 */
public class RegexMatches {

    public static void main(String args[]) {
//        String str = "尼格买提•阿姆西提";
//        String str = "王林123";
        String str = "王林";
        String pattern = "[\\u4E00-\\u9FA5]{2,5}(?:(·|•)[\\u4E00-\\u9FA5]{2,5})*";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        boolean matching = m.matches();
        System.out.println(matching);
    }

}
