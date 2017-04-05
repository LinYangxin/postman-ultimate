package com.example.panker.panker.ui.activity;

//import com.example.panker.panker.bean.config;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.panker.panker.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/14
 */
public class Activity_Login extends Activity implements View.OnClickListener {
    private TextView fogetpw, sign;
    private Button Login;
    private String id;
    private TextView UserPw;
    private TextView UserId;
//    private config c =new config();
    private void initView() {
        fogetpw = (TextView) findViewById(R.id.Login_forgetpw);
        sign = (TextView) findViewById(R.id.Login_sign);
        Login = (Button) findViewById(R.id.login_btn);
        UserId = (TextView) findViewById(R.id.login_id);
        UserPw = (TextView) findViewById(R.id.login_pw);
    }

    private void initEvents() {
        fogetpw.setOnClickListener(this);
        sign.setOnClickListener(this);
        Login.setOnClickListener(this);
       // IntentFilter filter = new IntentFilter(Activity_complete.action);
      //  registerReceiver(broadcastReceiver, filter);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 测试 SDK 是否正常工作的代码
       /* AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });*/
        initView();
        initEvents();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login_forgetpw:
                //Toast.makeText(this, "forget", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(Activity_Login.this,Activity_resetPassword.class);
                startActivity(intent1);
                break;
            case R.id.Login_sign:
                //Toast.makeText(this,"sign",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_Login.this, Activity_Sign.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                login();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
      //  unregisterReceiver(broadcastReceiver);
    }

    ;

    private void login() {
        id = UserId.getText().toString();
        if (id.isEmpty()) {
            Toast.makeText(Activity_Login.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (isMobileNumberValid(id)) {
                AVUser.loginByMobilePhoneNumberInBackground(id, UserPw.getText().toString(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            Intent intent=new Intent(Activity_Login.this,Activity_main.class);
                            startActivity(intent);
                            Activity_Login.this.finish();
                        } else {
                            Toast.makeText(Activity_Login.this, "账号不存在或密码错误", Toast.LENGTH_SHORT).show();
                            AVUser.logOut();
                        }
                    }
                });
            } else {
                AVUser.logInInBackground(id, UserPw.getText().toString(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            Toast.makeText(Activity_Login.this, "login success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Activity_Login.this,Activity_main.class);
                            startActivity(intent);
                            Activity_Login.this.finish();
                        } else {
                            Toast.makeText(Activity_Login.this, "账号不存在或密码错误", Toast.LENGTH_SHORT).show();
                            AVUser.logOut();
                        }
                    }
                });
            }
        }
    }


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
}
