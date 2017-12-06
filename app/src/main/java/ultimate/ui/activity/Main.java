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
import com.example.postman.ultimate.R;
//import com.example.ultimate.ultimate.uilt.Tools.Head;
import ultimate.ui.fragment.GameFragment;
import ultimate.ui.fragment.NewsFragment;
import ultimate.ui.fragment.ShoppingFragment;
import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TitleManager;
import ultimate.bean.User;
import ultimate.uilt.tools.BaseViewPager;
import ultimate.uilt.localdatabase.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ultimate.ui.fragment.MeFragment;

/**
 * Created by user on 2016/8/20.
 */
public class Main extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private BaseViewPager viewPager;
    private List<Fragment> fragments;
    // private BottomNavigationBar mBottomNavigationBar;
    private long firstTime = 0;
    private MeFragment meFragment = new MeFragment();
    private GameFragment gameFragment = new GameFragment();
    private ShoppingFragment shoppingFragment = new ShoppingFragment();
    private NewsFragment newsFragment = new NewsFragment();
    private TextView mTNews, mTShop, mTGame, mTMe;
    private TitleManager titleManager;
    public static SQLiteHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AVOSCloud.setDebugLogEnabled(true);
        setContentView(R.layout.guide);
        DataManager.user = new User(this);
        initView();
        initEvent();
    }

    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(newsFragment);
        fragments.add(shoppingFragment);
        fragments.add(gameFragment);
        fragments.add(meFragment);
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
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.ONLY_SETTING, getString(R.string.title));
        helper = new SQLiteHelper(this);
        mTNews = (TextView) findViewById(R.id.tvNews);
        mTShop = (TextView) findViewById(R.id.tvShop);
        mTGame = (TextView) findViewById(R.id.tvGame);
        mTMe = (TextView) findViewById(R.id.tvMe);
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
        titleManager.setLeftImage(View.GONE);
    }

    private void setTabState(TextView textView, int image, int color) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, image, 0, 0);//Call requires API level 17
        textView.setTextColor(color);
    }

    @Override
    public void onClick(View view) {
        resetTabState();
        switch (view.getId()) {
            case R.id.tvNews:
                titleManager.setLeftImage(View.GONE);
                setTabState(mTNews, R.drawable.news_black, ContextCompat.getColor(this, R.color.colorPrimary));
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tvShop:
                titleManager.setLeftImage(View.GONE);
                setTabState(mTShop, R.drawable.shoping_black, ContextCompat.getColor(this, R.color.colorPrimary));
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tvGame:
                titleManager.setLeftImage(View.GONE);
                setTabState(mTGame, R.drawable.game_black, ContextCompat.getColor(this, R.color.colorPrimary));
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.tvMe:
                titleManager.setLeftImage(View.VISIBLE);
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
            Toast.makeText(this, getString(R.string.back_msg), Toast.LENGTH_SHORT).show();
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
                String tmp = DataManager.user.getNickname();
                meFragment.tvNickname.setText(tmp);
                String string = DataManager.user.getTeam();
                meFragment.tvTeam.setText(string);
                meFragment.imgHead.setImageBitmap(PostmanHelper.toRoundCornerImage(DataManager.user.getHead(), 180));
                meFragment.tvMyself.setText(DataManager.user.getMyself());
                break;
            case 2:
                fragments.clear();
                Intent intent = new Intent(Main.this, Login.class);
                startActivity(intent);
                AVUser.getCurrentUser().logOut();
                finish();
                break;
            case RESULT_OK:
                if (requestCode == PostmanHelper.REQUEST_CODE_CAMERA) {
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
            DataManager.user.setBackground(bm);
            meFragment.rlMeData.setBackground(PostmanHelper.bitmap2drawable(DataManager.user.getBackground()));
            byte[] img_data;
//压缩成PNG
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, os);
//得到二进制数据*/
            img_data = os.toByteArray();

            AVFile file = new AVFile("head.png", img_data);
            DataManager.user.getMyUser().put("bg", file);
            DataManager.user.getMyUser().put("hasBackground", true);
            DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    DataManager.user.setBackground(bm);
                    DataManager.user.setHasBackground(true);
                    Toast.makeText(Main.this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveBackground(Intent data) {
        Uri uri = data.getData();
        ContentResolver cr = this.getContentResolver();
        try {
            final Bitmap bm = BitmapFactory.decodeStream(cr.openInputStream(uri));
            DataManager.user.setBackground(bm);
            meFragment.rlMeData.setBackground(PostmanHelper.bitmap2drawable(DataManager.user.getBackground()));
            byte[] img_data;
//压缩成PNG
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, os);
//得到二进制数据*/
            img_data = os.toByteArray();

            AVFile file = new AVFile("bg.png", img_data);
            DataManager.user.getMyUser().put("bg", file);
            DataManager.user.getMyUser().put("hasBackground", true);
            DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    DataManager.user.setBackground(bm);
                    DataManager.user.setHasBackground(true);
                    Toast.makeText(Main.this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
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
