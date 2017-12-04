package ultimate.uilt.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.panker.ultimate.R;
import ultimate.uilt.tools.SystemBarTintManager;

/**
 * Created by 51646 on 2017/4/16.
 */

public class SystemBarTintManagerHelper {
    public SystemBarTintManager tintManager;
    public SystemBarTintManagerHelper(Activity activity){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,activity);
        }
        tintManager = new SystemBarTintManager(activity);
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
