package com.example.panker.panker.ui.activity;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.example.panker.panker.R;
import com.example.panker.panker.ui.fragment.*;
//import com.example.panker.panker.uilt.Tools.Head;
import com.example.panker.panker.uilt.Tools.PankerHelper;
import com.example.panker.panker.uilt.Tools.SystemBarTintManager;
import com.example.panker.panker.uilt.Tools.TittleManager;
import com.example.panker.panker.bean.User;
import com.example.panker.panker.uilt.Tools.baseViewPager;
import com.example.panker.panker.uilt.local_db.SQLiteHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/20.
 */
public class Activity_main extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private baseViewPager viewPager;
    private List<Fragment> fragments;
   // private BottomNavigationBar mBottomNavigationBar;
    private long firstTime = 0;
    private me Me = new me();
    private game Game = new game();
    private shopping Shop = new shopping();
    private news News = new news();
    private User user;
    private TextView mTNews,mTShop,mTGame,mTMe;
    public  TittleManager tittleManager;
    public static SQLiteHelper helper;
    //private SystemBarTintManagerHelper tintManagerHelper;
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
        fragments.add(News);
        fragments.add(Shop);
        fragments.add(Game);
        fragments.add(Me);
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
        tittleManager=new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_TITLE,"翼鲲飞盘");
        helper=new SQLiteHelper(this);
        mTNews = (TextView)findViewById(R.id.tv_news);
        mTShop = (TextView)findViewById(R.id.tv_shop);
        mTGame = (TextView)findViewById(R.id.tv_game);
        mTMe = (TextView)findViewById(R.id.tv_me);
    }

    private void initEvent() {
        mTNews.setOnClickListener(this);
        mTShop.setOnClickListener(this);
        mTGame.setOnClickListener(this);
        mTMe.setOnClickListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        //set the defalut tab state
        setTabState(mTNews, R.drawable.news_black, ContextCompat.getColor(this,R.color.colorPrimary));
    }
    private void setTabState(TextView textView, int image, int color) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, image, 0, 0);//Call requires API level 17
        textView.setTextColor(color);
    }
    @Override
    public void onClick(View view) {
        resetTabState();
        switch (view.getId()) {
            case R.id.tv_news:
                setTabState(mTNews, R.drawable.news_black, ContextCompat.getColor(this,R.color.colorPrimary));
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tv_shop:
                setTabState(mTShop, R.drawable.shoping_black, ContextCompat.getColor(this,R.color.colorPrimary));
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tv_game:
                setTabState(mTGame, R.drawable.game_black, ContextCompat.getColor(this,R.color.colorPrimary));
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.tv_me:
                setTabState(mTMe, R.drawable.me_black,ContextCompat.getColor(this,R.color.colorPrimary));
                viewPager.setCurrentItem(3, true);
                break;
//            case R.id.me_power:
//                Intent intent = new Intent(Activity_main.this, Activity_me_power.class);
//                startActivity(intent);
//                break;
        }
    }
    private void resetTabState() {
        setTabState(mTNews, R.drawable.news_glay, R.color.black_light);
        setTabState(mTShop, R.drawable.shoping_glay,  R.color.black_light);
        setTabState(mTGame, R.drawable.game_glay, R.color.black_light);
        setTabState(mTMe, R.drawable.me_glay, R.color.black_light);

    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
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
                String tmp = user.getNickname();
                Me.tv_nickname.setText(tmp);
                String string = user.getTeam();
                Me.tv_team.setText(string);
                Me.me_head.setImageBitmap(PankerHelper.toRoundCornerImage(user.getHead(),180));
                Me.tv_myself.setText(user.getMyself());
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
