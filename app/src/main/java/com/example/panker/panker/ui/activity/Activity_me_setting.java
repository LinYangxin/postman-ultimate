package com.example.panker.panker.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.TittleManager;

/**
 * Created by user on 2016/9/10.
 */
public class Activity_me_setting extends Activity implements View.OnClickListener {
    private TittleManager tittleManager;
    private Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initEvent();
    }
    private void initView(){
        tittleManager=new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_BACK,"设置");
        btn_logout=(Button)findViewById(R.id.btn_logout);
    }
    private void initEvent(){
        btn_logout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                AlertDialog.Builder ab = new AlertDialog.Builder(Activity_me_setting.this);
                ab.setTitle("提示").setMessage("确认要退出吗").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Activity_me_setting.this.setResult(2);
                        finish();
                    }
                }).setNegativeButton("否",null).show();
                break;
        }
    }
}
