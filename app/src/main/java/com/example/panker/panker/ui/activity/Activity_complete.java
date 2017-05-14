package com.example.panker.panker.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.CheckHelper;
import com.example.panker.panker.uilt.Tools.SystemBarTintManager;
import com.example.panker.panker.uilt.Tools.TittleManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/19...
 */
public class Activity_complete extends Activity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    private TittleManager tittleManager;
    private RatingBar throwing;
    private RatingBar catching;
    private RatingBar D;
    private RatingBar O;
    private RatingBar speed;
    private float throwing_num;
    private float catching_num;
    private float D_num;
    private float O_num;
    private float speed_num;
    private EditText nickname;
    private EditText email;
    private EditText team;
    private RadioGroup radioGroup;
    private boolean isMan = true;
    private Button summit;
   // public static final String action = "Panker.broadcast.complete";

    private void initView() {
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_TITLE, "完善资料");
        throwing = (RatingBar) findViewById(R.id.throwing);
        catching = (RatingBar) findViewById(R.id.catching);
        D = (RatingBar) findViewById(R.id.D);
        O = (RatingBar) findViewById(R.id.O);
        speed = (RatingBar) findViewById(R.id.speed);
        nickname = (EditText) findViewById(R.id.sign_nickname);
        email = (EditText) findViewById(R.id.sign_email);
        team = (EditText) findViewById(R.id.sign_team);
        radioGroup = (RadioGroup) findViewById(R.id.sign_sex);
        summit = (Button) findViewById(R.id.sign_btn_summit);
    }

    private void initEvent() {
        throwing.setOnRatingBarChangeListener(this);
        catching.setOnRatingBarChangeListener(this);
        D.setOnRatingBarChangeListener(this);
        O.setOnRatingBarChangeListener(this);
        speed.setOnRatingBarChangeListener(this);
        summit.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkId) {
                if (checkId == R.id.man) {
                    isMan = true;
                } else if (checkId == R.id.woman) {
                    isMan = false;
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        initView();
        initEvent();

    }

    @Override
    public void onClick(View view) {
        String tmp_nickname = nickname.getText().toString();
        String tmp_email = email.getText().toString();
        String tmp_team = team.getText().toString();
        if (tmp_email == null || tmp_nickname == null || tmp_email == null || tmp_email.isEmpty() || tmp_nickname.isEmpty() || tmp_team.isEmpty() || TextUtils.isEmpty(tmp_email) || TextUtils.isEmpty(tmp_email) || TextUtils.isEmpty(tmp_nickname)) {
            Toast.makeText(this, "请完善资料", Toast.LENGTH_SHORT).show();
        } else if (CheckHelper.isEmailValid(tmp_email)) {
            AVUser.getCurrentUser().put("nickname", tmp_nickname);
            AVUser.getCurrentUser().setEmail(tmp_email);
            AVUser.getCurrentUser().put("team", tmp_team);
            AVUser.getCurrentUser().put("throwing", throwing_num);
            AVUser.getCurrentUser().put("catching", catching_num);
            AVUser.getCurrentUser().put("O", O_num);
            AVUser.getCurrentUser().put("D", D_num);
            AVUser.getCurrentUser().put("speed", speed_num);
            AVUser.getCurrentUser().put("isMan", isMan);
            AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                      //  sendBroadcast(new Intent(action));
                        AlertDialog.Builder ab = new AlertDialog.Builder(Activity_complete.this);
                        ab.setTitle("提示").setMessage("资料补充完成，请查收邮箱，完成邮箱核验").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AVUser.logOut();
                                Activity_complete.this.finish();
                            }
                        }).show();

                    } else
                        Toast.makeText(Activity_complete.this, "失败", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "邮箱格式有误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        switch (ratingBar.getId()) {
            case R.id.throwing:
                throwing_num = throwing.getRating();
                break;
            case R.id.catching:
                catching_num = catching.getRating();
                break;
            case R.id.D:
                D_num = D.getRating();
                break;
            case R.id.O:
                O_num = O.getRating();
                break;
            case R.id.speed:
                speed_num = speed.getRating();
                break;
        }
    }

//    //验证是否为邮箱
//    public static boolean isEmailValid(String email) {
//        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
//        Matcher m = p.matcher(email);
//        return m.matches();
//    }
}
