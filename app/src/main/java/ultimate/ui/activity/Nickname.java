package ultimate.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.example.postman.ultimate.R;
import ultimate.uilt.tools.TitleManager;
import ultimate.bean.User;

/**
 * Created by user on 2016/8/25.
 */
public class Nickname extends Activity {
    private TitleManager titleManager;
    //private AVUser myUser;
    private String new_nickname;
    private EditText editText;
    private User user;
    private final int REQUEST_NICKNAME=0,NOTHING=999;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata_settingifo);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
      //  myUser=AVUser.getCurrentUser();
        user=new User();
        new_nickname=user.getNickname();
    }

    private void initView() {
        titleManager =new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.BACK_AND_SAVE,"设置昵称");
        editText=(EditText)findViewById(R.id.et);
        editText.setText(new_nickname);

    }

    private void initEvent() {
        titleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_nickname=editText.getText().toString();
                if(TextUtils.isEmpty(new_nickname)||new_nickname.isEmpty()){
                    Toast.makeText(Nickname.this,"不能为空!",Toast.LENGTH_SHORT).show();
                }
                else{

                    user.getMyUser().put("nickname",new_nickname);
                    user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                Toast.makeText(Nickname.this,"保存成功!",Toast.LENGTH_SHORT).show();
                               // Intent intent=new Intent();
                                //intent.putExtra("new_nickname",new_nickname);
                                user.setNickname(new_nickname);
                                Nickname.this.setResult(REQUEST_NICKNAME);
                                Nickname.this.finish();
                            }
                            else{
                                Toast.makeText(Nickname.this,"失败!请检查网络",Toast.LENGTH_SHORT).show();
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
    public void onBackPressed(){
        //Intent intent = new Intent();
        Nickname.this.setResult(NOTHING);
        Nickname.this.finish();
    }
}
