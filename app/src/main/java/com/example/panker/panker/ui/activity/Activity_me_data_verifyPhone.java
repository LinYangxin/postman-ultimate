package com.example.panker.panker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.TittleManager;
import com.example.panker.panker.bean.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/8/25.
 */
public class Activity_me_data_verifyPhone extends Activity {
    private TittleManager tittleManager;
    private AVUser myUser;
    private String new_phone;
    private EditText editText;
    private User user;
    private final int REQUEST_NICKNAME=0,REQUEST_PHONE=1,REQUEST_TEAM=3,NOTHING=999;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata_settingifo);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        user=new User();
        new_phone=user.getPhonenumber();
    }

    private void initView() {
        tittleManager=new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.BACK_AND_STEP,"设置手机");
        editText=(EditText)findViewById(R.id.et);
        editText.setText(new_phone);

    }

    private void initEvent() {
        tittleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String string=editText.getText().toString();
                if(isMobileNumberValid(string)) {
                    user.getMyUser().setMobilePhoneNumber(string);
                    user.getMyUser().setUsername(string);
                    user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Intent intent = new Intent(Activity_me_data_verifyPhone.this, Activity_verifyPhone.class);
                                intent.putExtra("FromSignIn", false);
                                startActivity(intent);
                                user.setPhonenumber(string);
                                Activity_me_data_verifyPhone.this.setResult(REQUEST_PHONE);
                                Activity_me_data_verifyPhone.this.finish();
                            } else {
                                Toast.makeText(Activity_me_data_verifyPhone.this, "失败", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Activity_me_data_verifyPhone.this,"手机格式有误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tittleManager.setLeftTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed(){
        Activity_me_data_verifyPhone.this.setResult(NOTHING);
        Activity_me_data_verifyPhone.this.finish();
    }
    public static boolean isMobileNumberValid(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[1,3,5-8])|(14[5,7,9])|(18[0,2-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
