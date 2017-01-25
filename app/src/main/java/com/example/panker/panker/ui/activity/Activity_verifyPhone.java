package com.example.panker.panker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.TittleManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/18.
 */
public class Activity_verifyPhone extends Activity implements View.OnClickListener{
    private EditText mEditText;
    private TittleManager tittleManager;
    private Button verify_btn;
    private  boolean FromSignIn=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyphone);
        initData();
        initView();
        initEvent();
    }
    private void initData(){
        Intent intent=getIntent();
        FromSignIn=intent.getBooleanExtra("FromSignIn",true);
    }
    private void initView(){
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_TITLE, "手机验证");
        verify_btn=(Button)findViewById(R.id.btn_verify);
        mEditText=(EditText)findViewById(R.id.edit_verify);
    }
    private void initEvent(){
        verify_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String verifynumber=mEditText.getText().toString();
        if(isVerifyNumberValid(verifynumber)) {
            AVUser.verifyMobilePhoneInBackground(verifynumber, new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        if(FromSignIn) {
                            Intent intent = new Intent(Activity_verifyPhone.this, Activity_complete.class);
                            startActivity(intent);
                            Activity_verifyPhone.this.finish();
                        }
                        else {
                            Toast.makeText(Activity_verifyPhone.this,"修改成功",Toast.LENGTH_SHORT).show();
                            Activity_verifyPhone.this.finish();
                        }
                    } else {
                        Log.d("SMS", "Verified failed!");
                        Toast.makeText(Activity_verifyPhone.this,"验证码有误",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
            Toast.makeText(Activity_verifyPhone.this,"验证码有误",Toast.LENGTH_SHORT).show();
    }
    public static boolean isVerifyNumberValid(String verify) {
        Pattern p = Pattern.compile("^\\d{6}$");
        Matcher m = p.matcher(verify);
        return m.matches();
    }
}
