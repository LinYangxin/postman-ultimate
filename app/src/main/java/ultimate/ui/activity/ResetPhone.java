package ultimate.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
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
public class ResetPhone extends Activity {
    private TitleManager titleManager;
    private String phone;
    private EditText editText;
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
        phone= DataManager.user.getPhoneNumber();
    }

    private void initView() {
        titleManager =new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.BACK_AND_STEP,"设置手机");
        editText=(EditText)findViewById(R.id.et);
        editText.setText(phone);

    }

    private void initEvent() {
        titleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String string=editText.getText().toString();
                if(PostmanHelper.isMobileNumberValid(string)) {
                    DataManager.user.getMyUser().setMobilePhoneNumber(string);
                    DataManager.user.getMyUser().setUsername(string);
                    DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Intent intent = new Intent(ResetPhone.this, VerifyPhone.class);
                                intent.putExtra("FromSignIn", false);
                                startActivity(intent);
                                DataManager.user.setPhonenumber(string);
                                ResetPhone.this.setResult(REQUEST_PHONE);
                                ResetPhone.this.finish();
                            } else {
                                Toast.makeText(ResetPhone.this, PostmanHelper.getCodeFromServer(e), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(ResetPhone.this,"手机格式有误",Toast.LENGTH_SHORT).show();
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
    public void onBackPressed(){
        ResetPhone.this.setResult(NOTHING);
        ResetPhone.this.finish();
    }
}
