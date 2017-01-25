package com.example.panker.panker.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.TittleManager;
import com.example.panker.panker.uilt.Tools.User;


/**
 * Created by user on 2016/8/22.
 * Fenrir
 */
public class Activity_me_power extends Activity implements RatingBar.OnRatingBarChangeListener {
    private double O,D,Speed,Catching,Throwing;
   // private AVUser User;
    private User user;
    private RatingBar ratingBar_O,ratingBar_D,ratingBar_S,ratingBar_C,ratingBar_T;
    private TittleManager tittleManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mypower);
        initData();
        initView();
        initEvent();
    }
    private void initData(){
        user=new User();
        O= user.getO();
        D=user.getD();
        Speed=user.getSpeed();
        Catching=user.getCatching();
        Throwing=user.getThrowing();
    }
    private void initView(){
        ratingBar_O=(RatingBar)findViewById(R.id.O);
        ratingBar_D=(RatingBar)findViewById(R.id.D);
        ratingBar_S=(RatingBar)findViewById(R.id.speed);
        ratingBar_C=(RatingBar)findViewById(R.id.catching);
        ratingBar_T=(RatingBar)findViewById(R.id.throwing);
        ratingBar_O.setRating((float)O);
        ratingBar_D.setRating((float)D);
        ratingBar_S.setRating((float)Speed);
        ratingBar_C.setRating((float)Catching);
        ratingBar_T.setRating((float)Throwing);
        tittleManager=new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.BACK_AND_SAVE,"能力值");
    }
    private void initEvent(){
        ratingBar_O.setOnRatingBarChangeListener(this);
        ratingBar_D.setOnRatingBarChangeListener(this);
        ratingBar_S.setOnRatingBarChangeListener(this);
        ratingBar_T.setOnRatingBarChangeListener(this);
        ratingBar_C.setOnRatingBarChangeListener(this);
        tittleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.getMyUser().put("O",O);
                user.getMyUser().put("D",D);
                user.getMyUser().put("speed",Speed);
                user.getMyUser().put("catching",Catching);
                user.getMyUser().put("throwing",Throwing);
                user.getMyUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null){
                            Toast.makeText(Activity_me_power.this,"更新成功！",Toast.LENGTH_SHORT).show();
                            user.setO((float)O);
                            user.setD((float)D);
                            user.setSpeed((float)Speed);
                            user.setCatching((float)Catching);
                            user.setThrowing((float)Throwing);
                            Activity_me_power.this.finish();
                        }
                        else{
                            Toast.makeText(Activity_me_power.this,"失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        tittleManager.setLeftTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        switch (ratingBar.getId()) {
            case R.id.throwing:
                Throwing = ratingBar_T.getRating();
                break;
            case R.id.catching:
                Catching = ratingBar_C.getRating();
                break;
            case R.id.D:
                D = ratingBar_D.getRating();
                break;
            case R.id.O:
                O = ratingBar_O.getRating();
                break;
            case R.id.speed:
                Speed = ratingBar_S.getRating();
                break;
        }
    }
}
