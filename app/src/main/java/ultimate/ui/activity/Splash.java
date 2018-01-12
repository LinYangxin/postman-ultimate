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
import android.util.Log;
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
import com.example.postman.ultimate.R;

import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.PostmanHelper;

import java.util.List;


/**
 * Created by user on 2016/8/5.
 */
public class Splash extends Activity {
    // private final String TAG = this.getClass().getSimpleName();
    private ImageView ivStart;
    private final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1, REQUEST_CODE_CAMERA = 2;//用以动态获取权限
    //private static DataManager dataManager;//此处从服务器处加载数据。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        // dataManager = new DataManager();

        GetPomitssion(0);
    }

    private void initImage() {
        Log.i("Splash.java", "初始化图片");
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
                if (currentUser != null && currentUser.isMobilePhoneVerified()) {
                    // 跳转到首页
                    startActivity(Main.class);
                } else {
                    //缓存用户对象为空时，可打开用户注册界面…
                    startActivity(Login.class);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivStart.startAnimation(scaleAnim);
        Log.i("Splash.java", "初始化图片完成");
    }

    private void startActivity(Class cls) {
        Intent intent = new Intent(Splash.this, cls);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    public void GetPomitssion(int f) {
        Log.i("Splash.java", "获取权限");
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("Splash.java", "SDK版本大于23，需要动态获取敏感权限");
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.CAMERA);
            int checkCallPhonePermission1 = ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (checkCallPhonePermission == PackageManager.PERMISSION_GRANTED && checkCallPhonePermission1 == PackageManager.PERMISSION_GRANTED) {
                Continued();
                return;
            }
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED && f == 0) {
                ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
            if (checkCallPhonePermission1 != PackageManager.PERMISSION_GRANTED && f == 1) {
                ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
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
                    Toast.makeText(this, getString(R.string.data_get_permission_failed), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // SetHeadFromCamera();
                    GetPomitssion(1);
                } else {
                    // Permission Denied
                    Toast.makeText(this, getString(R.string.data_get_permission_failed), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void Continued() {
        Log.i("Splash.java", "从服务器中获取欢迎页面");
        ivStart = (ImageView) findViewById(R.id.iv_start);
        AVQuery<AVObject> avQuery = new AVQuery<>("Splash");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                Log.i("Splash.java", "判断是否需要加载欢迎页面");
                if (e == null) {
                    boolean flag = list.get(0).getBoolean("updata");
                    if (flag) {
                        Log.i("Splash.java", "从服务器中获取欢迎页面");
                        list.get(0).getAVFile("Pic").getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Log.i("Splash.java", "欢迎页面获取成功");
                                    ivStart.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                    initImage();
                                } else {
                                    Log.e("Splash.java","获取图片失败，原因为："+e.getMessage());
                                }
                            }
                        });
                    } else {
                        Log.i("Splash.java","无需加载欢迎页面，采用默认");
                        ivStart.setImageResource(R.drawable.start);
                        initImage();
                    }
                } else {
                    Log.e("Splash.java", e.getMessage());
                }
            }
        });
    }
}
