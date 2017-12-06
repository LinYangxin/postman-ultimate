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
import com.example.postman.ultimate.R;

import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TitleManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/8/6.
 */
public class ResetPassword extends Activity {
    private TitleManager titleManager;
    private Button btnNext;
    private LinearLayout linearLayoutVerify;
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
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.ONLY_BACK, getString(R.string.resetpasseord_title));
        btnNext = (Button) findViewById(R.id.reset_btn);
        linearLayoutVerify = (LinearLayout) findViewById(R.id.reset_ll);
        inputID = (EditText) findViewById(R.id.edit_id);
        inputVerify = (EditText) findViewById(R.id.edit_verify);
        inputNewPw = (EditText) findViewById(R.id.edit_newpw);
    }

    private void initEvent() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = inputID.getText().toString();
                if (tmp.isEmpty()) {
                    Toast.makeText(ResetPassword.this, getString(R.string.not_null), Toast.LENGTH_SHORT).show();
                } else {
                    if (PostmanHelper.isMobileNumberValid(tmp)&&step%2==1) {
                        linearLayoutVerify.setVisibility(View.VISIBLE);
                        step++;
                        AVUser.requestPasswordResetBySmsCodeInBackground(tmp, new RequestMobileCodeCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    AlertDialog.Builder ab = new AlertDialog.Builder(ResetPassword.this);
                                    ab.setTitle(getString(R.string.tips)).setMessage(getString(R.string.resetpassword_msg_phone)).setPositiveButton(getString(R.string.i_know), null).show();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else if(PostmanHelper.isMobileNumberValid(tmp)&&step%2==0){
                        String tmp_verify = inputVerify.getText().toString();
                        String tmp_new = inputNewPw.getText().toString();
                        if (PostmanHelper.isVerifyNumberValid(tmp_verify)&&tmp_new.length()>5&&tmp_new.length()<20) {
                            AVUser.resetPasswordBySmsCodeInBackground(tmp_verify, tmp_new, new UpdatePasswordCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        Toast.makeText(ResetPassword.this, getString(R.string.reset_success), Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ResetPassword.this,PostmanHelper.getCodeFromServer(e),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(ResetPassword.this,getString(R.string.resetpassword_error_msg),Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (PostmanHelper.isEmailValid(tmp)) {
                        linearLayoutVerify.setVisibility(View.GONE);
                        String tmp_email = inputID.getText().toString();
                        AVUser.requestPasswordResetInBackground(tmp_email, new RequestPasswordResetCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    AlertDialog.Builder ab = new AlertDialog.Builder(ResetPassword.this);
                                    ab.setTitle(getString(R.string.tips)).setMessage(getString(R.string.resetpassword_msg_email)).setPositiveButton(getString(R.string.i_know), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            ResetPassword.this.finish();
                                        }
                                    }).show();

                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(ResetPassword.this, getString(R.string.resetpassword_error_msg), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
