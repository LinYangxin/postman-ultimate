package com.example.panker.panker.uilt.Tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.avos.avoscloud.AVException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/5/13.
 */

public class PankerHelper {
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
    /**
     * 获取圆角位图的方法
     *
     * @param bitmap
     *            需要转化成圆角的位图
     * @param pixels
     *            圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCornerImage(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        // 抗锯齿
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    //bitmap2drawable
    public static Drawable bitmap2drawable(Bitmap bitmap){
        return new BitmapDrawable(bitmap);
    }

    public static String SystemTime2String(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分ss秒");
        Date date = new Date(time);
        return formatter.format(date);
    }
}
