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
import com.example.postman.ultimate.R;

import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TitleManager;
import ultimate.bean.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/8/25.
 */
public class Email extends Activity {
    private TitleManager titleManager;
    //  private AVUser myUser;
    private String email;
    private EditText editText;
    private final int REQUEST_EMAIL = 2, NOTHING = 999;
   // private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata_myself);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        email = DataManager.user.getEmail();
    }

    private void initView() {
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.BACK_AND_SAVE, "设置邮箱");
        editText = (EditText) findViewById(R.id.et);
        editText.setText(email);
    }

    private void initEvent() {
        titleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editText.getText().toString();
                if (TextUtils.isEmpty(email) || email.isEmpty() || !PostmanHelper.isEmailValid(email)) {
                    Toast.makeText(Email.this, "不能为空或格式有误!", Toast.LENGTH_SHORT).show();
                } else {
                    DataManager.user.getMyUser().setEmail(email);
                    DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                AlertDialog.Builder ab = new AlertDialog.Builder(Email.this);
                                ab.setTitle("提示").setMessage("资料补充完成，请查收邮箱，完成邮箱核验").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DataManager.user.setEmail(email);
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
        titleManager.setLeftTitleListener(new View.OnClickListener() {
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
}
