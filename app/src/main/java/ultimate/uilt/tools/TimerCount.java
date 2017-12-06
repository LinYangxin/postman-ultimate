package ultimate.uilt.tools;

/**
 * Created by user on 2016/7/16.
 */
import android.os.CountDownTimer;
import android.widget.Button;

public class TimerCount extends CountDownTimer {
    private Button btn;

    public TimerCount(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    public TimerCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        btn.setClickable(true);
        btn.setText("获取验证码");
    }

    @Override
    public void onTick(long arg0) {
        // TODO Auto-generated method stub
        btn.setClickable(false);
        btn.setText(arg0 / 1000 + "秒后重试");
    }
}
