package ultimate.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.example.panker.ultimate.R;
import ultimate.uilt.Tools.TittleManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/8/6.
 */
public class Activity_resetPassword extends Activity {
    private TittleManager tittleManager;
    private Button next;
    private LinearLayout verify;
    private EditText inputID;
    private EditText inputVerify;
    private EditText inputNewPw;
    private int step=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        initView();
        initEvent();
    }

    private void initView() {
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_BACK, "重置密码");
        next = (Button) findViewById(R.id.reset_btn);
        verify = (LinearLayout) findViewById(R.id.reset_ll);
        inputID = (EditText) findViewById(R.id.edit_id);
        inputVerify = (EditText) findViewById(R.id.edit_verify);
        inputNewPw = (EditText) findViewById(R.id.edit_newpw);
    }

    private void initEvent() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = inputID.getText().toString();
                if (tmp.isEmpty()) {
                    Toast.makeText(Activity_resetPassword.this, "不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (isMobileNumberValid(tmp)&&step%2==1) {
                        verify.setVisibility(View.VISIBLE);
                        step++;
                        AVUser.requestPasswordResetBySmsCodeInBackground(tmp, new RequestMobileCodeCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                   /* String tmp_verify = inputVerify.getText().toString();
                                    String tmp_new = inputNewPw.getText().toString();
                                    if (isVerifyNumberValid(tmp_verify)&&tmp_new.length()>6&&tmp_new.length()<20) {
                                        AVUser.resetPasswordBySmsCodeInBackground(tmp_verify, tmp_new, new UpdatePasswordCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                if (e == null) {
                                                    Toast.makeText(Activity_resetPassword.this, "重置成功", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(Activity_resetPassword.this,"验证码错误",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(Activity_resetPassword.this,"验证码或新密码格式错误",Toast.LENGTH_SHORT).show();
                                    }*/
                                    AlertDialog.Builder ab = new AlertDialog.Builder(Activity_resetPassword.this);
                                    ab.setTitle("提示").setMessage("我们已发送验证码至您的手机，请注意查收，按操作完成密码重置").setPositiveButton("知道了", null).show();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else if(isMobileNumberValid(tmp)&&step%2==0){
                        String tmp_verify = inputVerify.getText().toString();
                        String tmp_new = inputNewPw.getText().toString();
                        if (isVerifyNumberValid(tmp_verify)&&tmp_new.length()>5&&tmp_new.length()<20) {
                            AVUser.resetPasswordBySmsCodeInBackground(tmp_verify, tmp_new, new UpdatePasswordCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        Toast.makeText(Activity_resetPassword.this, "重置成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(Activity_resetPassword.this,"验证码错误",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Activity_resetPassword.this,"验证码或新密码格式错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (isEmail(tmp)) {
                        verify.setVisibility(View.GONE);
                        String tmp_email = inputID.getText().toString();
                        AVUser.requestPasswordResetInBackground(tmp_email, new RequestPasswordResetCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    AlertDialog.Builder ab = new AlertDialog.Builder(Activity_resetPassword.this);
                                    ab.setTitle("提示").setMessage("我们已发送邮件至该邮箱，请注意查收，按操作完成密码重置").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Activity_resetPassword.this.finish();
                                        }
                                    }).show();

                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Activity_resetPassword.this, "输入格式有误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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

    /**
     * 验证邮箱格式
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isVerifyNumberValid(String verify) {
        Pattern p = Pattern.compile("^\\d{6}$");
        Matcher m = p.matcher(verify);
        return m.matches();
    }
}
