package ultimate.ui.activity;

import android.app.Activity;
import android.os.Bundle;
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

/**
 * Created by user on 2016/8/25.
 */
public class Myself extends Activity {
    private TitleManager titleManager;
  //  private AVUser myUser;
    private String myself;
    private EditText editText;
    private final int REQUEST_myself = 0,REQUEST_MYSELF=6,NOTHING=999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata_myself);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        myself = DataManager.user.getMyself();
    }

    private void initView() {
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.BACK_AND_SAVE, getString(R.string.myself_title));
        editText = (EditText) findViewById(R.id.et);
        editText.setText(myself);
    }

    private void initEvent() {
        titleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myself = editText.getText().toString();
                DataManager.user.getMyUser().put("myself", myself);
                DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(Myself.this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                            DataManager.user.setMyself(myself);
                            Myself.this.setResult(REQUEST_MYSELF);
                            Myself.this.finish();
                        } else {
                            Toast.makeText(Myself.this, PostmanHelper.getCodeFromServer(e), Toast.LENGTH_SHORT).show();
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
    public void onBackPressed(){
        Myself.this.setResult(NOTHING);
        Myself.this.finish();
    }
}
