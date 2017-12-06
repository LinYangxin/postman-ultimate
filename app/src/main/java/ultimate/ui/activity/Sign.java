package ultimate.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.postman.ultimate.R;
import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TimerCount;
import ultimate.uilt.tools.TitleManager;

/**
 * Created by user on 2016/7/15.
 */
public class Sign extends Activity implements View.OnClickListener {
    private TitleManager titleManager;
    private EditText mPhoneNumber;//手机号码
    private EditText mPassword;//设置密码
    private EditText mPasswordCheck;//确认密码
    private EditText mVerifyNumber;
    private EditText mEmail;
    private Button btnNextStep;//下一步
    private Button btnVerifyNumber;
    private TimerCount btnGetVerifyNumber;

    //private  AVUser user;
    //private boolean check_verify = false;//验证码验证状态
    //初始化界面ui
    private void initView() {
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.ONLY_BACK, "账号注册");
        mPhoneNumber = (EditText) findViewById(R.id.sign_mobilephonenumber);
        mPassword = (EditText) findViewById(R.id.sign_pw);
        mPasswordCheck = (EditText) findViewById(R.id.sign_pwcheck);
        mEmail = (EditText) findViewById(R.id.sign_email);
        btnNextStep = (Button) findViewById(R.id.sign_btn_nextstep);
        btnVerifyNumber = (Button) findViewById(R.id.sign_btn_getVerifyNumber);
        btnGetVerifyNumber = new TimerCount(60000, 1000, btnVerifyNumber);
        mVerifyNumber = (EditText) findViewById(R.id.sign_verifyNumber);
    }

    //初始化事件
    private void initEvent() {
        btnNextStep.setOnClickListener(this);
        btnVerifyNumber.setOnClickListener(this);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initView();
        initEvent();
    }

    //注册
    private void SignUp() {
        final AVUser user = AVUser.getCurrentUser();
        AVUser.verifyMobilePhoneInBackground(mVerifyNumber.getText().toString(), new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    user.setPassword(mPassword.getText().toString());// 设置密码
                    user.setEmail(mEmail.getText().toString());
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            AlertDialog.Builder ab = new AlertDialog.Builder(Sign.this);
                            ab.setTitle("提示").setMessage("资料补充完成，请查收邮箱，完成邮箱核验").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AVUser.logOut();
                                    Sign.this.finish();
                                }
                            }).show();
                        }
                    });
                } else {
                    Toast.makeText(Sign.this, PostmanHelper.getCodeFromServer(e), Toast.LENGTH_SHORT).show();
                    try {
                        user.delete();
                    } catch (AVException E) {

                    }
                }
            }
        });
    }

    //按钮点击响应函数
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_btn_nextstep://下一步
                String t1 = mPassword.getText().toString();
                String t2 = mPasswordCheck.getText().toString();
                String t3 = mEmail.getText().toString();
                if (t1.isEmpty() || t2.isEmpty()) {
                    Toast.makeText(Sign.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (t3.isEmpty()) {
                    Toast.makeText(Sign.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!PostmanHelper.isEmailValid(t3)) {
                    Toast.makeText(Sign.this, "邮箱格式有误", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (PostmanHelper.isRightPassword(t1, t2)) {
                    SignUp();
                } else
                    Toast.makeText(Sign.this, "密码前后不一致或不符合格式要求", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_btn_getVerifyNumber:
                String p = mPhoneNumber.getText().toString();
                if (PostmanHelper.isMobileNumberValid(p) && !p.isEmpty()) {
                    final AVUser user = new AVUser();
                    user.setUsername(mPhoneNumber.getText().toString());
                    user.setPassword("888888888");
                    user.setMobilePhoneNumber(mPhoneNumber.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                try {
                                    Toast.makeText(Sign.this, "用户已存在", Toast.LENGTH_SHORT).show();
                                    user.delete();
                                } catch (AVException E) {

                                }
                            } else {
                                btnGetVerifyNumber.start();
                                Toast.makeText(Sign.this, "已发送验证码", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Sign.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

