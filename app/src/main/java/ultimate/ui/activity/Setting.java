package ultimate.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.postman.ultimate.R;

import ultimate.uilt.tools.SystemBarTintManagerHelper;
import ultimate.uilt.tools.TitleManager;

/**
 * Created by user on 2016/9/10.
 */
public class Setting extends Activity implements View.OnClickListener {
    private TitleManager titleManager;
    private Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initEvent();
    }

    private void initView() {
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.ONLY_BACK, "设置");
        btnLogout = (Button) findViewById(R.id.btn_logout);
    }

    private void initEvent() {
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                AlertDialog.Builder ab = new AlertDialog.Builder(Setting.this);
                ab.setTitle("提示").setMessage("确认要退出吗").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Setting.this.setResult(2);
                        finish();
                    }
                }).setNegativeButton("否", null).show();
                break;
            default:
                break;
        }
    }
}
