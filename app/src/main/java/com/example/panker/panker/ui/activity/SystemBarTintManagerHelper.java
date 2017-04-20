package com.example.panker.panker.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.SystemBarTintManager;

/**
 * Created by 51646 on 2017/4/16.
 */

public class SystemBarTintManagerHelper {
    SystemBarTintManager tintManager;
    public SystemBarTintManagerHelper(Activity activity){
        tintManager = new SystemBarTintManager(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,activity);
        }
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.red_light);
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on,Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
