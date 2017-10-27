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
import com.example.panker.ultimate.R;
import ultimate.uilt.Tools.PankerHelper;
import ultimate.uilt.Tools.TimerCount;
import ultimate.uilt.Tools.TittleManager;

/**
 * Created by user on 2016/7/15.
 */
public class Activity_Sign extends Activity implements View.OnClickListener {
    private TittleManager tittleManager;
    private EditText mPhonenumber;//手机号码
    private EditText mPw;//设置密码
    private EditText mPwcheck;//确认密码
    private EditText mVerifyNumber;
    private EditText mEmail;
    private Button btn_nextstep;//下一步
    private Button btn_VerifyNumber;
    private TimerCount btn_getVerifyNumber;
    private boolean hadVerified = false;

    //private  AVUser user;
    //private boolean check_verify = false;//验证码验证状态
    //初始化界面ui
    private void initView() {
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_BACK, "账号注册");
        mPhonenumber = (EditText) findViewById(R.id.sign_mobilephonenumber);
        mPw = (EditText) findViewById(R.id.sign_pw);
        mPwcheck = (EditText) findViewById(R.id.sign_pwcheck);
        mEmail = (EditText) findViewById(R.id.sign_email);
        btn_nextstep = (Button) findViewById(R.id.sign_btn_nextstep);
        btn_VerifyNumber = (Button) findViewById(R.id.sign_btn_getVerifyNumber);
        btn_getVerifyNumber = new TimerCount(60000, 1000, btn_VerifyNumber);
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
        final AVUser user = AVUser.getCurrentUser();
        AVUser.verifyMobilePhoneInBackground(mVerifyNumber.getText().toString(), new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    user.setPassword(mPw.getText().toString());// 设置密码
//            user.setMobilePhoneNumber(mPhonenumber.getText().toString());
                    user.setEmail(mEmail.getText().toString());
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            AlertDialog.Builder ab = new AlertDialog.Builder(Activity_Sign.this);
                            ab.setTitle("提示").setMessage("资料补充完成，请查收邮箱，完成邮箱核验").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AVUser.logOut();
                                    Activity_Sign.this.finish();
                                }
                            }).show();
                        }
                    });
                } else {
                    Toast.makeText(Activity_Sign.this, "验证码有误", Toast.LENGTH_SHORT).show();
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
                String t1 = mPw.getText().toString();
                String t2 = mPwcheck.getText().toString();
                String t3 = mEmail.getText().toString();
                if (t1.isEmpty() || t2.isEmpty()) {
                    Toast.makeText(Activity_Sign.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (t3.isEmpty()) {
                    Toast.makeText(Activity_Sign.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!PankerHelper.isEmailValid(t3)) {
                    Toast.makeText(Activity_Sign.this, "邮箱格式有误", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (PankerHelper.isRightPassword(t1, t2)) {
                    SignUp();
                } else
                    Toast.makeText(Activity_Sign.this, "密码前后不一致或不符合格式要求", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_btn_getVerifyNumber:
                String p = mPhonenumber.getText().toString();
                if (PankerHelper.isMobileNumberValid(p) && !p.isEmpty()) {
                    final AVUser user = new AVUser();
                    user.setUsername(mPhonenumber.getText().toString());
                    user.setPassword("888888888");
                    user.setMobilePhoneNumber(mPhonenumber.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                try {
                                    Toast.makeText(Activity_Sign.this, "用户已存在", Toast.LENGTH_SHORT).show();
                                    user.delete();
                                } catch (AVException E) {

                                }
                            } else {
                                btn_getVerifyNumber.start();
                                Toast.makeText(Activity_Sign.this, "已发送验证码", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//                        AVOSCloud.requestSMSCodeInBackground(mPhonenumber.getText().toString(), new RequestMobileCodeCallback() {
//                            @Override
//                            public void done(AVException e) {
//                                btn_getVerifyNumber.start();
//                                Toast.makeText(Activity_Sign.this, "已发送验证码", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    } catch (Exception e) {
//                    }
                } else {
                    Toast.makeText(Activity_Sign.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


//    /**
//     * 验证手机号是否符合大陆的标准格式
//     *
//     * @param mobiles
//     * @return
//     */
//
//    public static boolean isMobileNumberValid(String mobiles) {
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[1,3,5-8])|(14[5,7,9])|(18[0,2-9]))\\d{8}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
//    }

}

