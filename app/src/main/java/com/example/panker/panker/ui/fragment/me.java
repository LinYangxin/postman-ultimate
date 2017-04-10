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
//        helper.insert_users("15151829298",null,"Douer",0,0,null,null);
//        String sql="select nickname from "+ helper.TABLE_NAME_Users+" where telephone = "+"15151829298";
//        cursor=new Cursor() {
//            @Override
//            public int getCount() {
//                return 0;
//            }
//
//            @Override
//            public int getPosition() {
//                return 0;
//            }
//
//            @Override
//            public boolean move(int offset) {
//                return false;
//            }
//
//            @Override
//            public boolean moveToPosition(int position) {
//                return false;
//            }
//
//            @Override
//            public boolean moveToFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean moveToLast() {
//                return false;
//            }
//
//            @Override
//            public boolean moveToNext() {
//                return false;
//            }
//
//            @Override
//            public boolean moveToPrevious() {
//                return false;
//            }
//
//            @Override
//            public boolean isFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isLast() {
//                return false;
//            }
//
//            @Override
//            public boolean isBeforeFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isAfterLast() {
//                return false;
//            }
//
//            @Override
//            public int getColumnIndex(String columnName) {
//                return 0;
//            }
//
//            @Override
//            public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
//                return 0;
//            }
//
//            @Override
//            public String getColumnName(int columnIndex) {
//                return null;
//            }
//
//            @Override
//            public String[] getColumnNames() {
//                return new String[0];
//            }
//
//            @Override
//            public int getColumnCount() {
//                return 0;
//            }
//
//            @Override
//            public byte[] getBlob(int columnIndex) {
//                return new byte[0];
//            }
//
//            @Override
//            public String getString(int columnIndex) {
//                return null;
//            }
//
//            @Override
//            public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
//
//            }
//
//            @Override
//            public short getShort(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public int getInt(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public long getLong(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public float getFloat(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public double getDouble(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public int getType(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public boolean isNull(int columnIndex) {
//                return false;
//            }
//
//            @Override
//            public void deactivate() {
//
//            }
//
//            @Override
//            public boolean requery() {
//                return false;
//            }
//
//            @Override
//            public void close() {
//
//            }
//
//            @Override
//            public boolean isClosed() {
//                return false;
//            }
//
//            @Override
//            public void registerContentObserver(ContentObserver observer) {
//
//            }
//
//            @Override
//            public void unregisterContentObserver(ContentObserver observer) {
//
//            }
//
//            @Override
//            public void registerDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public void unregisterDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public void setNotificationUri(ContentResolver cr, Uri uri) {
//
//            }
//
//            @Override
//            public Uri getNotificationUri() {
//                return null;
//            }
//
//            @Override
//            public boolean getWantsAllOnMoveCalls() {
//                return false;
//            }
//
//            @Override
//            public void setExtras(Bundle extras) {
//
//            }
//
//            @Override
//            public Bundle getExtras() {
//                return null;
//            }
//
//            @Override
//            public Bundle respond(Bundle extras) {
//                return null;
//            }
//        };
//        cursor=helper.query(sql,null);
//        if(cursor.getCount()==0)
//            System.out.println("ddddddddddddddddddd");
//        cursor.moveToFirst();
//        nickname=cursor.getString(0);
//
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