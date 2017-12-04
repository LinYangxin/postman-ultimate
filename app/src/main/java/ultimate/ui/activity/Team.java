package ultimate.ui.activity;

import android.app.Activity;
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

/**
 * Created by user on 2016/8/25.
 */
public class Team extends Activity {
    private TittleManager tittleManager;
  //  private AVUser myUser;
    private String new_team;
    private EditText editText;
    private User user;
    private final int REQUEST_NICKNAME=0,REQUEST_TEAM=3,NOTHING=999;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata_settingifo);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        user=new User();
        new_team=user.getTeam();
    }

    private void initView() {
        tittleManager=new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.BACK_AND_SAVE,"设置队伍");
        editText=(EditText)findViewById(R.id.et);
        editText.setText(new_team);

    }

    private void initEvent() {
        tittleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_team=editText.getText().toString();
                if(TextUtils.isEmpty(new_team)||new_team.isEmpty()){
                    Toast.makeText(Team.this,"不能为空!",Toast.LENGTH_SHORT).show();
                }
                else{
                   user.getMyUser().put("team",new_team);
                    user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                Toast.makeText(Team.this,"保存成功!",Toast.LENGTH_SHORT).show();
                               user.setTeam(new_team);
                                Team.this.setResult(REQUEST_TEAM);
                                Team.this.finish();
                            }
                            else{
                                Toast.makeText(Team.this,"失败!请检查网络",Toast.LENGTH_SHORT).show();
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
    public void onBackPressed(){
        Team.this.setResult(NOTHING);
        Team.this.finish();
    }
}
