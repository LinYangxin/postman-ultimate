package ultimate.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.example.panker.ultimate.R;
import ultimate.uilt.tools.TittleManager;
import ultimate.bean.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/8/25.
 */
public class Email extends Activity {
    private TittleManager tittleManager;
    //  private AVUser myUser;
    private String new_email;
    private EditText editText;
    private final int REQUEST_EMAIL = 2, NOTHING = 999;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata_myself);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        user = new User();
        new_email = user.getEmail();
    }

    private void initView() {
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.BACK_AND_SAVE, "设置邮箱");
        editText = (EditText) findViewById(R.id.et);
        editText.setText(new_email);
    }

    private void initEvent() {
        tittleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_email = editText.getText().toString();
                if (TextUtils.isEmpty(new_email) || new_email.isEmpty() || !isEmailValid(new_email)) {
                    Toast.makeText(Email.this, "不能为空或格式有误!", Toast.LENGTH_SHORT).show();
                } else {
                    user.getMyUser().setEmail(new_email);
                    user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                AlertDialog.Builder ab = new AlertDialog.Builder(Email.this);
                                ab.setTitle("提示").setMessage("资料补充完成，请查收邮箱，完成邮箱核验").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        user.setEmail(new_email);
                                        Email.this.setResult(REQUEST_EMAIL);
                                        Email.this.finish();
                                    }
                                }).show();

                            } else {
                                Toast.makeText(Email.this, "失败!请检查网络", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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

    public void onBackPressed() {
        Intent intent = new Intent();
        Email.this.setResult(NOTHING, intent);
        Email.this.finish();
    }

    //验证是否为邮箱
    public static boolean isEmailValid(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
