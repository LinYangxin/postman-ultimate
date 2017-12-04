package ultimate.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.panker.ultimate.R;
//import com.example.ultimate.ultimate.uilt.Tools.Head;
import ultimate.ui.fragment.News;
import ultimate.uilt.tools.PankerHelper;
import ultimate.uilt.tools.TittleManager;
import ultimate.bean.User;
import ultimate.uilt.tools.BaseViewPager;
import ultimate.uilt.localdatabase.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ultimate.ui.fragment.Game;
import ultimate.ui.fragment.Me;
import ultimate.ui.fragment.Shopping;

/**
 * Created by user on 2016/8/20.
 */
public class Main extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private BaseViewPager viewPager;
    private List<Fragment> fragments;
    // private BottomNavigationBar mBottomNavigationBar;
    private long firstTime = 0;
    private ultimate.ui.fragment.Me Me = new Me();
    private ultimate.ui.fragment.Game Game = new Game();
    private Shopping Shop = new Shopping();
    private ultimate.ui.fragment.News News = new News();
    private User user;
    private TextView mTNews, mTShop, mTGame, mTMe;
    private TittleManager tittleManager;
    public static SQLiteHelper helper;
    //private SystemBarTintManagerHelper tintManagerHelper;
    private final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1, REQUEST_CODE_CAMERA = 2;//用以动态获取权限
    private final int REQUEST_BY_CAMERA = 110, REQUEST_BY_GALLERY = 111, REQUEST_BY_CROP = 112;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AVOSCloud.setDebugLogEnabled(true);
        setContentView(R.layout.guide);
        user = new User(this);
        initView();
        initEvent();
    }

    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(News);
        fragments.add(Shop);
        fragments.add(Game);
        fragments.add(Me);
        viewPager = (BaseViewPager) findViewById(R.id.fragments_container);
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
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_SETTING, "极限飞盘");
        helper = new SQLiteHelper(this);
        mTNews = (TextView) findViewById(R.id.tv_news);
        mTShop = (TextView) findViewById(R.id.tv_shop);
        mTGame = (TextView) findViewById(R.id.tv_game);
        mTMe = (TextView) findViewById(R.id.tv_me);
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
        setTabState(mTNews, R.drawable.news_black, ContextCompat.getColor(this, R.color.colorPrimary));
        tittleManager.setLeftImage(View.GONE);
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
                tittleManager.setLeftImage(View.GONE);
                setTabState(mTNews, R.drawable.news_black, ContextCompat.getColor(this, R.color.colorPrimary));
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tv_shop:
                tittleManager.setLeftImage(View.GONE);
                setTabState(mTShop, R.drawable.shoping_black, ContextCompat.getColor(this, R.color.colorPrimary));
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tv_game:
                tittleManager.setLeftImage(View.GONE);
                setTabState(mTGame, R.drawable.game_black, ContextCompat.getColor(this, R.color.colorPrimary));
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.tv_me:
                tittleManager.setLeftImage(View.VISIBLE);
                setTabState(mTMe, R.drawable.me_black, ContextCompat.getColor(this, R.color.colorPrimary));
                viewPager.setCurrentItem(3, true);
                break;
//            case R.id.me_power:
//                Intent intent = new Intent(Main.this, Power.class);
//                startActivity(intent);
//                break;
        }
    }

    private void resetTabState() {
        setTabState(mTNews, R.drawable.news_glay, R.color.black_light);
        setTabState(mTShop, R.drawable.shoping_glay, R.color.black_light);
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
                Me.me_head.setImageBitmap(PankerHelper.toRoundCornerImage(user.getHead(), 180));
                Me.tv_myself.setText(user.getMyself());
                break;
            case 2:
                fragments.clear();
                Intent intent = new Intent(Main.this, Login.class);
                startActivity(intent);
                AVUser.getCurrentUser().logOut();
                finish();
                break;
            case RESULT_OK:
                if (requestCode == PankerHelper.REQUEST_CODE_CAMERA) {
                    saveBackground_camera(data);
                } else {
                    saveBackground(data);
                }
                break;
        }
    }

    private void saveBackground_camera(Intent data) {
        if (data == null) {
            return;
        } else {
            Bundle extras = data.getExtras();
            final Bitmap bm = extras.getParcelable("data");
            user.setBackground(bm);
            Me.me_data.setBackground(PankerHelper.bitmap2drawable(user.getBackground()));
            byte[] img_data;
//压缩成PNG
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, os);
//得到二进制数据*/
            img_data = os.toByteArray();

            AVFile file = new AVFile("head.png", img_data);
            user.getMyUser().put("bg", file);
            user.getMyUser().put("hasBackground", true);
            user.getMyUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    user.setBackground(bm);
                    user.setHasBackground(true);
                    Toast.makeText(Main.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveBackground(Intent data) {
        Uri uri = data.getData();
        ContentResolver cr = this.getContentResolver();
        try {
            final Bitmap bm = BitmapFactory.decodeStream(cr.openInputStream(uri));
            user.setBackground(bm);
            Me.me_data.setBackground(PankerHelper.bitmap2drawable(user.getBackground()));
            byte[] img_data;
//压缩成PNG
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, os);
//得到二进制数据*/
            img_data = os.toByteArray();

            AVFile file = new AVFile("bg.png", img_data);
            user.getMyUser().put("bg", file);
            user.getMyUser().put("hasBackground", true);
            user.getMyUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    user.setBackground(bm);
                    user.setHasBackground(true);
                    Toast.makeText(Main.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
            });
            if(bm!=null)
                bm.recycle();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
