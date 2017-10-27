package ultimate.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ultimate.ui.activity.Activity_showhead;
import ultimate.bean.User;
import ultimate.ui.activity.Activity_me_data;
import com.example.panker.ultimate.R;
import ultimate.uilt.Tools.PankerHelper;

/**
 * Created by Ivory on 2016/7/19.
 * 天才兜儿又来啦！！！
 * hahahaha
 */
public class me extends basefragment implements View.OnClickListener {
    private View mContent;
    private String nickname,team,myself;
    public TextView tv_nickname, tv_team, tv_myself,mEdit;
//    private LinearLayout me_power, me_setting;
    public RelativeLayout me_data;
    public ImageView me_head;
    private User user;
    private Cursor cursor;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_me, container, false);
        me_head = (ImageView) mContent.findViewById(R.id.me_head);
        tv_nickname = (TextView) mContent.findViewById(R.id.me_nickname);
        tv_team = (TextView) mContent.findViewById(R.id.me_team);
        tv_myself = (TextView) mContent.findViewById(R.id.me_myself);
        mEdit = (TextView)mContent.findViewById(R.id.me_edit);
        tv_nickname.setText(nickname);
        tv_team.setText(team);
        tv_myself.setText(myself);

//        me_power = (LinearLayout) mContent.findViewById(R.id.me_power);
//        me_setting = (LinearLayout) mContent.findViewById(R.id.me_setting);
        me_data = (RelativeLayout) mContent.findViewById(R.id.rl);
//        Toast.makeText(mActivity,user.getBackground().toString(),Toast.LENGTH_LONG).show();
        me_data.setBackground(PankerHelper.bitmap2drawable(user.getBackground()));
        me_head.setImageBitmap(PankerHelper.toRoundCornerImage(user.getHead(), 180));
        return mContent;
    }

    @Override
    protected void initData() {
        // head=new Head(0);
        super.initData();
        user = new User(mActivity);
        nickname = user.getNickname();
        team = user.getTeam();
        myself = user.getMyself();
    }

    @Override
    protected void initEvent() {
//        me_power.setOnClickListener(this);
        me_data.setOnClickListener(this);
//        me_setting.setOnClickListener(this);
        me_head.setOnClickListener(this);
        mEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.me_edit:
                intent = new Intent(mActivity, Activity_me_data.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.me_head:
                intent = new Intent(mActivity, Activity_showhead.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.rl:
                Toast.makeText(mActivity,"hahah",Toast.LENGTH_SHORT).show();
                setBackground();
                break;
        }
    }
    private void setBackground() {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(mActivity)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if( which == 0 ){
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PankerHelper.REQUEST_BY_GALLERY);
                        }else{
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PankerHelper.REQUEST_CODE_CAMERA);
                        }
                    }
                })
                .create().show();
    }
}