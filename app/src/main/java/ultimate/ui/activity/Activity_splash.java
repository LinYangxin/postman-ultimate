package ultimate.ui.activity;

//import com.example.ultimate.ultimate.ui.fragment.e;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.example.panker.ultimate.R;
import ultimate.uilt.Tools.DataManager;

import java.util.List;


/**
 * Created by user on 2016/8/5.
 */
public class Activity_splash extends Activity {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView iv_start;
    private final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1, REQUEST_CODE_CAMERA = 2;//用以动态获取权限
    private static DataManager dataManager;//此处从服务器处加载数据。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        dataManager = new DataManager();
        dataManager.init();
        GetPomitssion(0);
    }

    private void initImage() {
        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                AVUser currentUser = AVUser.getCurrentUser();
                if (currentUser != null&&currentUser.isMobilePhoneVerified()) {
                    // 跳转到首页
                    startActivity(Activity_main.class);
                } else {
                    //缓存用户对象为空时，可打开用户注册界面…
                    startActivity(Activity_Login.class);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_start.startAnimation(scaleAnim);
    }

    private void startActivity(Class cls) {
        Intent intent = new Intent(Activity_splash.this, cls);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    public void GetPomitssion(int f) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(Activity_splash.this, Manifest.permission.CAMERA);
            int checkCallPhonePermission1 = ContextCompat.checkSelfPermission(Activity_splash.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (checkCallPhonePermission == PackageManager.PERMISSION_GRANTED && checkCallPhonePermission1 == PackageManager.PERMISSION_GRANTED) {
                Continued();
                return;
            }
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED && f == 0) {
                ActivityCompat.requestPermissions(Activity_splash.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
            if (checkCallPhonePermission1 != PackageManager.PERMISSION_GRANTED && f == 1) {
                ActivityCompat.requestPermissions(Activity_splash.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
                return;
            }
        } else Continued();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Continued();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // SetHeadFromCamera();
                    GetPomitssion(1);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void Continued() {
        iv_start = (ImageView) findViewById(R.id.iv_start);
        AVQuery<AVObject> avQuery = new AVQuery<>("Splash");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                boolean flag = list.get(0).getBoolean("updata");
                if (flag) {
                    list.get(0).getAVFile("Pic").getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, AVException e) {
                            if (e == null) {
                                iv_start.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                initImage();
                            }
                        }
                    });
                } else {
                    iv_start.setImageResource(R.drawable.start);
                    initImage();
                }
            }
        });
    }
}
