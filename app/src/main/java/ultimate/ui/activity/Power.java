package ultimate.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.example.postman.ultimate.R;

import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TitleManager;
import ultimate.bean.User;


/**
 * Created by user on 2016/8/22.
 * Fenrir
 */
public class Power extends Activity implements RatingBar.OnRatingBarChangeListener {
    private double Offensive,Defense,Speed,Catching,Throwing;
   // private AVUser User;
    private RatingBar ratingBarOffensive,ratingBarDefense,ratingBarSpeed,ratingBarCatching,ratingBarThrowing;
    private TitleManager titleManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mypower);
        initData();
        initView();
        initEvent();
    }
    private void initData(){
        Offensive= DataManager.user.getO();
        Defense=DataManager.user.getD();
        Speed=DataManager.user.getSpeed();
        Catching=DataManager.user.getCatching();
        Throwing=DataManager.user.getThrowing();
    }
    private void initView(){
        ratingBarOffensive=(RatingBar)findViewById(R.id.O);
        ratingBarDefense=(RatingBar)findViewById(R.id.D);
        ratingBarSpeed=(RatingBar)findViewById(R.id.speed);
        ratingBarCatching=(RatingBar)findViewById(R.id.catching);
        ratingBarThrowing=(RatingBar)findViewById(R.id.throwing);
        ratingBarOffensive.setRating((float)Offensive);
        ratingBarDefense.setRating((float)Defense);
        ratingBarSpeed.setRating((float)Speed);
        ratingBarCatching.setRating((float)Catching);
        ratingBarThrowing.setRating((float)Throwing);
        titleManager =new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.BACK_AND_SAVE,getString(R.string.power_title));
    }
    private void initEvent(){
        ratingBarOffensive.setOnRatingBarChangeListener(this);
        ratingBarDefense.setOnRatingBarChangeListener(this);
        ratingBarSpeed.setOnRatingBarChangeListener(this);
        ratingBarThrowing.setOnRatingBarChangeListener(this);
        ratingBarCatching.setOnRatingBarChangeListener(this);
        titleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataManager.user.getMyUser().put("O",Offensive);
                DataManager.user.getMyUser().put("D",Defense);
                DataManager.user.getMyUser().put("speed",Speed);
                DataManager.user.getMyUser().put("catching",Catching);
                DataManager.user.getMyUser().put("throwing",Throwing);
                DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null){
                            Toast.makeText(Power.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
                            DataManager.user.setO((float)Offensive);
                            DataManager.user.setD((float)Defense);
                            DataManager.user.setSpeed((float)Speed);
                            DataManager.user.setCatching((float)Catching);
                            DataManager.user.setThrowing((float)Throwing);
                            Power.this.finish();
                        } else{
                            Toast.makeText(Power.this, PostmanHelper.getCodeFromServer(e),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        titleManager.setLeftTitleListener(new View.OnClickListener() {
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
                Throwing = ratingBarThrowing.getRating();
                break;
            case R.id.catching:
                Catching = ratingBarCatching.getRating();
                break;
            case R.id.D:
                Defense = ratingBarDefense.getRating();
                break;
            case R.id.O:
                Offensive = ratingBarOffensive.getRating();
                break;
            case R.id.speed:
                Speed = ratingBarSpeed.getRating();
                break;
        }
    }

}
