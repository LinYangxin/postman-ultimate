package com.example.panker.panker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.TimerCount;
import com.example.panker.panker.uilt.Tools.TittleManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/15.
 */
public class Activity_Sign extends Activity implements View.OnClickListener {
    private TittleManager tittleManager;
    private EditText mPhonenumber;//手机号码
   // private EditText mUserId;//验证码
    private EditText mPw;//设置密码
    private EditText mPwcheck;//确认密码
    private EditText mVerifyNumber;
    private Button btn_nextstep;//下一步
    private Button btn_VerifyNumber;
    private static long lastClick;
    private TimerCount btn_getVerifyNumber;
    //private boolean check_verify = false;//验证码验证状态
    //初始化界面ui
    private void initView() {
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_BACK, "账号注册");

        mPhonenumber = (EditText) findViewById(R.id.sign_mobilephonenumber);
       // mUserId = (EditText) findViewById(R.id.sign_id);
        mPw = (EditText) findViewById(R.id.sign_pw);
        mPwcheck = (EditText) findViewById(R.id.sign_pwcheck);
        btn_nextstep = (Button) findViewById(R.id.sign_btn_nextstep);
        btn_VerifyNumber = (Button)findViewById(R.id.sign_btn_getVerifyNumber);
        btn_getVerifyNumber = new TimerCount(60000,1000,btn_VerifyNumber);
        mVerifyNumber = (EditText) findViewById(R.id.sign_verifyNumber);
    }

    //初始化事件
    private void initEvent() {
        btn_nextstep.setOnClickListener(this);
        btn_VerifyNumber.setOnClickListener(this);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initView();
        initEvent();
    }

    //注册
    private void SignUp() {
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(mPhonenumber.getText().toString());// 设置用户名
        user.setPassword(mPw.getText().toString());// 设置密码
        user.setMobilePhoneNumber(mPhonenumber.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Intent intent=new Intent(Activity_Sign.this,Activity_verifyPhone.class);
                    startActivity(intent);
                    Activity_Sign.this.finish();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    Toast.makeText(Activity_Sign.this,"用户已存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //按钮点击响应函数
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_btn_nextstep://下一步
                String t1 = mPw.getText().toString();
                String t2 = mPwcheck.getText().toString();
                if (t1.isEmpty() || t2.isEmpty()) {
                    Toast.makeText(Activity_Sign.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (isRightPassword(t1, t2)) {
                    SignUp();
                } else
                    Toast.makeText(Activity_Sign.this, "密码前后不一致或不符合格式要求", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_btn_getVerifyNumber:
                btn_getVerifyNumber.start();
                Toast.makeText(Activity_Sign.this, "已发送验证码", Toast.LENGTH_SHORT).show();
                try {
                    lastClick = System.currentTimeMillis();
                    //AVUser.requestMobilePhoneVerify(mPhonenumber.getText().toString());
                    AVOSCloud.requestSMSCode(mPhonenumber.getText().toString());
                }catch (Exception e){
                }
                break;
        }
    }

    public boolean isRightPassword(String a, String b) {
        if (a.length() < 6 || a.length() > 20)
            return false;
        else
            return a.equals(b);
    }

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

}

