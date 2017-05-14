package com.example.panker.panker.uilt.Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/5/13.
 */

public class CheckHelper {
    /**
     * 验证手机号是否符合大陆的标准格式
     *
     * @param mobiles
     * @return
     */

    public static boolean isMobileNumberValid(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[1,3,5-8])|(14[5,7,9])|(18[0,2-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    //验证是否为邮箱
    public static boolean isEmailValid(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }
    //验证前后是否一致
    public static boolean isRightPassword(String a, String b) {
        if (a.length() < 6 || a.length() > 20)
            return false;
        else
            return a.equals(b);
    }
}
