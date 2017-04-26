package com.example.panker.panker.ui.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.panker.panker.ui.activity.Activity_showhead;
import com.example.panker.panker.bean.User;
import com.example.panker.panker.ui.activity.Activity_me_data;
import com.example.panker.panker.ui.activity.Activity_me_power;
import com.example.panker.panker.R;
import com.example.panker.panker.ui.activity.Activity_me_setting;

import static com.example.panker.panker.ui.activity.Activity_main.helper;

/**
 * Created by Ivory on 2016/7/19.
 * 天才兜儿又来啦！！！
 * hahahaha
 */
public class me extends basefragment implements View.OnClickListener {
    private View mContent;
    private String nickname;
    private String team;
    public TextView tv_nickname,tv_team;
    private LinearLayout me_power,me_setting;
    private RelativeLayout me_data;
    public  ImageView me_head;
    private User user;
    private Cursor cursor;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent=inflater.inflate(R.layout.fragment_me,container,false);
        me_head=(ImageView)mContent.findViewById(R.id.me_head);
        tv_nickname=(TextView)mContent.findViewById(R.id.me_nickname);
        tv_team=(TextView)mContent.findViewById(R.id.me_team);
        tv_nickname.setText("昵称："+nickname);
            tv_team.setText("效力于："+team);
        me_power=(LinearLayout)mContent.findViewById(R.id.me_power);
        me_setting=(LinearLayout)mContent.findViewById(R.id.me_setting);
        me_data=(RelativeLayout)mContent.findViewById(R.id.rl);
        if(user.getFlag()==false){
            me_head.setImageBitmap(user.getHead());
        }

        return mContent;
    }

    @Override
    protected void initData() {
       // head=new Head(0);
        super.initData();
        user=new User();
        nickname=user.getNickname();
        team=user.getTeam();
    }

    @Override
    protected void initEvent() {
        me_power.setOnClickListener(this);
        me_data.setOnClickListener(this);
        me_setting.setOnClickListener(this);
        me_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.me_power:
                intent=new Intent(mActivity,Activity_me_power.class);
                startActivity(intent);
                break;
            case R.id.rl:
                intent=new Intent(mActivity, Activity_me_data.class);
                startActivityForResult(intent,0);
                break;
            case R.id.me_setting:
                intent=new Intent(mActivity, Activity_me_setting.class);
                startActivityForResult(intent,0);
                break;
            case R.id.me_head:
                intent=new Intent(mActivity, Activity_showhead.class);
                startActivityForResult(intent,0);
                break;
        }
    }
}