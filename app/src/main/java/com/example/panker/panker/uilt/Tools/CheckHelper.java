package com.example.panker.panker.uilt.Tools;

import com.avos.avoscloud.AVException;

import java.text.DateFormat;
import java.util.Date;
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
    //处理服务器返回值错误代码
    public static String getCodeFromServer(AVException e){
        int code = e.getCode();
        switch (code){
            case 0:return "网络异常";
            case 1:return "网络异常";
            case 100:return "网络异常";
            case 202:return "用户名已被占用";
            case 203:return "邮箱已被占用";
            case 210:return "账户密码不匹配";
            case 211:return "用户不存在";
            case 214:return "手机已被占用";
            case 215:return "手机未验证";
            case 216:return "邮箱未验证";
            case 217:return "无效的用户名";
            case 219:return "登录失败次数超过限制，请稍候再试，或者通过忘记密码重设密码";
            case 603:return "验证码错误";
            default:return "未知的错误";
        }
    }
    //处理从服务器中获取的时间date
    public static String Date2String(Date date){
        String s = DateFormat.getDateInstance().format(date);
        return s;
    }
}
