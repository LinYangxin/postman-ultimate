package com.example.panker.panker.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.panker.panker.ui.activity.Activity_main;

/**
 * Created by user on 2016/8/20.
 */
public abstract class basefragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected Activity_main mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (Activity_main) getActivity();
        initData();
         View mContentView = initView(inflater, container, savedInstanceState);
        initEvent();
        return mContentView;
    }
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initEvent();

    /**
     * 初始化数据加载
     */
    protected void initData() {
    }

    /*public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    protected <T> void startActivity(Class<T> cls) {
        mActivity.startActivity(new Intent(mActivity, cls));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mActivity.onActivityResult(requestCode, resultCode, data);
    }

    protected void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

}
