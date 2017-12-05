package ultimate.ui.activity;

//import com.example.ultimate.ultimate.bean.config;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.postman.ultimate.R;
import ultimate.uilt.tools.PostmanHelper;

/**
 * Created by user on 2016/7/14
 */
public class Login extends Activity implements View.OnClickListener {
    private TextView forgetPassword, sign;
    private Button btnLogin;
    private String id;
    private EditText userPassword , userId;

    //    private config c =new config();
    private void initView() {
        forgetPassword = (TextView) findViewById(R.id.tvForgetPassword);
        sign = (TextView) findViewById(R.id.tvSign);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        userId = (EditText) findViewById(R.id.etId);
        userPassword = (EditText) findViewById(R.id.etPassword);
    }

    private void initEvents() {
        forgetPassword.setOnClickListener(this);
        sign.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        // IntentFilter filter = new IntentFilter(Complete.action);
        //  registerReceiver(broadcastReceiver, filter);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvents();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvForgetPassword:
                //Toast.makeText(this, "forget", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ultimate.ui.activity.Login.this, ResetPassword.class);
                startActivity(intent1);
                break;
            case R.id.tvSign:
                //Toast.makeText(this,"sign",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ultimate.ui.activity.Login.this, Sign.class);
                startActivity(intent);
                break;
            case R.id.btnLogin:
                doLogin();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        //  unregisterReceiver(broadcastReceiver);
    }

    ;

    private void doLogin() {
        id = userId.getText().toString();
        if (id.isEmpty()) {
            Toast.makeText(ultimate.ui.activity.Login.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (PostmanHelper.isMobileNumberValid(id)) {
                AVUser.loginByMobilePhoneNumberInBackground(id, userPassword.getText().toString(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            Intent intent = new Intent(ultimate.ui.activity.Login.this, Main.class);
                            startActivity(intent);
                            ultimate.ui.activity.Login.this.finish();
                        } else {
                            Toast.makeText(ultimate.ui.activity.Login.this, PostmanHelper.getCodeFromServer(e), Toast.LENGTH_SHORT).show();
                            AVUser.logOut();
                        }
                    }
                });
            } else {
                AVUser.logInInBackground(id, userPassword.getText().toString(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            Toast.makeText(ultimate.ui.activity.Login.this, "login success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ultimate.ui.activity.Login.this, Main.class);
                            startActivity(intent);
                            ultimate.ui.activity.Login.this.finish();
                        } else {
                            Toast.makeText(ultimate.ui.activity.Login.this, PostmanHelper.getCodeFromServer(e), Toast.LENGTH_SHORT).show();
                            AVUser.logOut();
                        }
                    }
                });
            }
        }
    }
}
