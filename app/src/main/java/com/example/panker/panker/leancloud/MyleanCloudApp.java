package com.example.panker.panker.leancloud;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by user on 2016/7/15.
 * leancloud的初始化，作用不明，参照demo。
 */
public class MyleanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"7V4zq0wjN4yJdvwAbLABnd9f-gzGzoHsz","xYj2q0pv51pVgeB4EBFWdoPk");
    }
}
