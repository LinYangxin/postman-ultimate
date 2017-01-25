package com.example.panker.panker.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.panker.panker.R;
import com.example.panker.panker.uilt.Tools.TittleManager;

/**
 * Created by Ivory on 2016/7/19.
 * 天才兜儿又来啦！！！
 * hahahaha
 */
public class game extends basefragment implements View.OnClickListener {
    private View mContent;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent=inflater.inflate(R.layout.fragment_me,container,false);
        return mContent;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {
    }
}