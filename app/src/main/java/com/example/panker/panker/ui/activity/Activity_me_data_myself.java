package com.example.panker.panker.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.TittleManager;
import com.example.panker.panker.bean.User;

/**
 * Created by user on 2016/8/25.
 */
public class Activity_me_data_myself extends Activity {
    private TittleManager tittleManager;
  //  private AVUser myUser;
    private String new_myself;
    private EditText editText;
    private User user;
    private final int REQUEST_myself = 0,REQUEST_MYSELF=6,NOTHING=999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata_myself);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        user=new User();
        new_myself = user.getMyself();
    }

    private void initView() {
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.BACK_AND_SAVE, "个人宣言");
        editText = (EditText) findViewById(R.id.et);
        editText.setText(new_myself);
    }

    private void initEvent() {
        tittleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_myself = editText.getText().toString();
                user.getMyUser().put("myself", new_myself);
                user.getMyUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(Activity_me_data_myself.this, "保存成功!", Toast.LENGTH_SHORT).show();
                           user.setMyself(new_myself);
                            Activity_me_data_myself.this.setResult(REQUEST_MYSELF);
                            Activity_me_data_myself.this.finish();
                        } else {
                            Toast.makeText(Activity_me_data_myself.this, "失败!请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
        Activity_me_data_myself.this.setResult(NOTHING);
        Activity_me_data_myself.this.finish();
    }
}
