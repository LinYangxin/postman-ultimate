package ultimate.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.postman.ultimate.R;
import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TitleManager;

/**
 * Created by user on 2016/7/19...
 */
public class Complete extends Activity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    private TitleManager titleManager;
    private RatingBar throwing,catching,defense,offensive,speed;
    private float throwingNum,catchingNum,defenseNum,offensiveNum,speedNum;
    private EditText nickname,email,team;
    private RadioGroup radioGroup;
    private boolean isMan = true;
    private Button summit;

    private void initView() {
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.ONLY_TITLE, "完善资料");
        throwing = (RatingBar) findViewById(R.id.rb_throwing);
        catching = (RatingBar) findViewById(R.id.rb_catching);
        defense = (RatingBar) findViewById(R.id.rb_defense);
        offensive = (RatingBar) findViewById(R.id.rb_offensive);
        speed = (RatingBar) findViewById(R.id.rb_speed);
        nickname = (EditText) findViewById(R.id.et_nickname);
        email = (EditText) findViewById(R.id.et_email);
        team = (EditText) findViewById(R.id.et_team);
        radioGroup = (RadioGroup) findViewById(R.id.rg_sex);
        summit = (Button) findViewById(R.id.btn_summit);
    }

    private void initEvent() {
        throwing.setOnRatingBarChangeListener(this);
        catching.setOnRatingBarChangeListener(this);
        defense.setOnRatingBarChangeListener(this);
        offensive.setOnRatingBarChangeListener(this);
        speed.setOnRatingBarChangeListener(this);
        summit.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkId) {
                if (checkId == R.id.rbtn_man) {
                    isMan = true;
                } else if (checkId == R.id.rbtn_woman) {
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
        } else if (PostmanHelper.isEmailValid(tmp_email)) {
            AVUser.getCurrentUser().put("nickname", tmp_nickname);
            AVUser.getCurrentUser().setEmail(tmp_email);
            AVUser.getCurrentUser().put("team", tmp_team);
            AVUser.getCurrentUser().put("throwing", throwingNum);
            AVUser.getCurrentUser().put("catching", catchingNum);
            AVUser.getCurrentUser().put("O", offensiveNum);
            AVUser.getCurrentUser().put("D", defenseNum);
            AVUser.getCurrentUser().put("speed", speedNum);
            AVUser.getCurrentUser().put("isMan", isMan);
            AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                      //  sendBroadcast(new Intent(action));
                        AlertDialog.Builder ab = new AlertDialog.Builder(Complete.this);
                        ab.setTitle("提示").setMessage("资料补充完成，请查收邮箱，完成邮箱核验").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AVUser.logOut();
                                Complete.this.finish();
                            }
                        }).show();

                    } else
                        Toast.makeText(Complete.this, "失败", Toast.LENGTH_SHORT).show();
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
                throwingNum = throwing.getRating();
                break;
            case R.id.catching:
                catchingNum = catching.getRating();
                break;
            case R.id.D:
                defenseNum = defense.getRating();
                break;
            case R.id.O:
                offensiveNum = offensive.getRating();
                break;
            case R.id.speed:
                speedNum = speed.getRating();
                break;
        }
    }
}
