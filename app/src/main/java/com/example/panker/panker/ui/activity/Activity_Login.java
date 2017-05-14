package com.example.panker.panker.ui.activity;

//import com.example.panker.panker.bean.config;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.CheckHelper;
import com.example.panker.panker.uilt.Tools.SystemBarTintManager;

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
        Login = (Button) findViewById(R.id.login);
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
                Intent intent1 = new Intent(Activity_Login.this, Activity_resetPassword.class);
                startActivity(intent1);
                break;
            case R.id.Login_sign:
                //Toast.makeText(this,"sign",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_Login.this, Activity_Sign.class);
                startActivity(intent);
                break;
            case R.id.login:
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
            if (CheckHelper.isMobileNumberValid(id)) {
                AVUser.loginByMobilePhoneNumberInBackground(id, UserPw.getText().toString(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            Intent intent = new Intent(Activity_Login.this, Activity_main.class);
                            startActivity(intent);
                            Activity_Login.this.finish();
                        } else {
                            Toast.makeText(Activity_Login.this, CheckHelper.getCodeFromServer(e), Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(Activity_Login.this, Activity_main.class);
                            startActivity(intent);
                            Activity_Login.this.finish();
                        } else {
                            Toast.makeText(Activity_Login.this, CheckHelper.getCodeFromServer(e), Toast.LENGTH_SHORT).show();
                            AVUser.logOut();
                        }
                    }
                });
            }
        }
    }
}
