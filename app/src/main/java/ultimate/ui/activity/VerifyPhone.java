package ultimate.ui.activity;

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
import com.example.postman.ultimate.R;

import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TitleManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/18.
 */
public class VerifyPhone extends Activity implements View.OnClickListener{
    private EditText mEditText;
    private TitleManager titleManager;
    private Button btnVerify;
    private  boolean fromSignIn=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyphone);
        initData();
        initView();
        initEvent();
    }
    private void initData(){
        Intent intent=getIntent();
        fromSignIn=intent.getBooleanExtra("FromSignIn",true);
    }
    private void initView(){
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.ONLY_TITLE, "手机验证");
        btnVerify=(Button)findViewById(R.id.btn_verify);
        mEditText=(EditText)findViewById(R.id.edit_verify);
    }
    private void initEvent(){
        btnVerify.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String verifynumber=mEditText.getText().toString();
        if(PostmanHelper.isVerifyNumberValid(verifynumber)) {
            AVUser.verifyMobilePhoneInBackground(verifynumber, new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        if(fromSignIn) {
                            Intent intent = new Intent(VerifyPhone.this, Complete.class);
                            startActivity(intent);
                            VerifyPhone.this.finish();
                        }
                        else {
                            Toast.makeText(VerifyPhone.this,"修改成功",Toast.LENGTH_SHORT).show();
                            VerifyPhone.this.finish();
                        }
                    } else {
                        Log.d("SMS", "Verified failed!");
                        Toast.makeText(VerifyPhone.this, PostmanHelper.getCodeFromServer(e),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
            Toast.makeText(VerifyPhone.this,"验证码格式有误",Toast.LENGTH_SHORT).show();
    }
}
