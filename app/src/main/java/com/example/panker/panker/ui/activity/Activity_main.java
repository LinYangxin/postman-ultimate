package com.example.panker.panker.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.ui.fragment.*;
//import com.example.panker.panker.uilt.Tools.Head;
import com.example.panker.panker.uilt.Tools.TittleManager;
import com.example.panker.panker.bean.User;
import com.example.panker.panker.uilt.Tools.baseViewPager;
import com.example.panker.panker.uilt.local_db.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/20.
 */
public class Activity_main extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private baseViewPager viewPager;
    private RadioGroup group;
    private RadioButton mNewBtn, mShopBtn, mGameBtn, mBtn;
    private List<Fragment> fragments;
    private long firstTime = 0;
    private me d = new me();
    private User user;
    private Cursor cursor;
//    private LinearLayout roll;
    public static TittleManager tittleManager;
    public static SQLiteHelper helper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AVOSCloud.setDebugLogEnabled(true);
        setContentView(R.layout.guide);
        initView();
        initEvent();
    }

    private void initView() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                user=new User();
                user.init();
                if (user.getFlag() == false)
                    user.getMyUser().getAVFile("head").getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, AVException e) {
                            if (e == null) {
                                user.setHead(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                            }
                        }
                    });
                else {
                    Resources res = getResources();
                    user.setHead(BitmapFactory.decodeResource(res, R.drawable.head));
                }

            }
        });
        t.start();
        fragments = new ArrayList<>();
        fragments.add(new news());
        fragments.add(new shopping());
        fragments.add(new game());
        fragments.add(d);
//        roll=(LinearLayout)findViewById(R.id.roll);
        mNewBtn = (RadioButton) findViewById(R.id.RadioButton);
        mShopBtn = (RadioButton) findViewById(R.id.RadioButton2);
        mGameBtn = (RadioButton) findViewById(R.id.RadioButton3);
        mBtn = (RadioButton) findViewById(R.id.RadioButton4);
        viewPager = (baseViewPager) findViewById(R.id.fragments_container);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        });

        viewPager.addOnPageChangeListener(this);
        group = (RadioGroup) findViewById(R.id.tab_btn_group);
        tittleManager=new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_TITLE,"发现");
        helper=new SQLiteHelper(this);
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
//       // helper.insert_users("15151829888",null,"Douer",0,0,null,null);
//        String sql="select * from "+ helper.TABLE_NAME_Users;
//        cursor=helper.query(sql,null);
//        if(cursor.getCount()==0)
//            System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
    }

    private void initEvent() {
        mNewBtn.setOnClickListener(this);
        mGameBtn.setOnClickListener(this);
        mShopBtn.setOnClickListener(this);
        mBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RadioButton:
                viewPager.setCurrentItem(0, true);
                findViewById(R.id.public_tittle).setVisibility(View.VISIBLE);
                tittleManager.setTitleName("发现");
                break;
            case R.id.RadioButton2:
                viewPager.setCurrentItem(1, true);
                findViewById(R.id.public_tittle).setVisibility(View.GONE);
               // tittleManager.setTitleName("商店");
                break;
            case R.id.RadioButton3:
                viewPager.setCurrentItem(2, true);
                findViewById(R.id.public_tittle).setVisibility(View.VISIBLE);
                tittleManager.setTitleName("比赛");
                break;
            case R.id.RadioButton4:
                viewPager.setCurrentItem(3, true);
                findViewById(R.id.public_tittle).setVisibility(View.VISIBLE);
                tittleManager.setTitleName("我的");
                break;
            case R.id.me_power:
                Intent intent = new Intent(Activity_main.this, Activity_me_power.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {


    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        switch (arg0) {
            case 0:
                group.check(R.id.RadioButton);
                break;
            case 1:
                group.check(R.id.RadioButton2);
                break;
            case 2:
                group.check(R.id.RadioButton3);
                break;
            case 3:
                group.check(R.id.RadioButton4);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return false;
    }


    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                String tmp = "昵称：" + user.getNickname();
                d.tv_nickname.setText(tmp);
                String string = "效力于：" + user.getTeam();
                d.tv_team.setText(string);
                d.me_head.setImageBitmap(user.getHead());
                break;
            case 2:
                fragments.clear();
                Intent intent = new Intent(Activity_main.this, Activity_Login.class);
                startActivity(intent);
                AVUser.getCurrentUser().logOut();
                finish();
                break;
        }

    }
}
