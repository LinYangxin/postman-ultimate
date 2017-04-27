package com.example.panker.panker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.panker.panker.R;
import com.example.panker.panker.bean.Game;
import com.example.panker.panker.bean.News;
import com.example.panker.panker.ui.activity.Activity_web;
import com.example.panker.panker.ui.adapter.game_adapter;
import com.example.panker.panker.uilt.MyListView.MyListView;
import com.example.panker.panker.uilt.Tools.DataManager;


/**
 * Created by Ivory on 2016/7/19.
 * hahahaha
 */
public class game extends basefragment implements View.OnClickListener {
    private View mContent;
    private static DataManager dataManager = new DataManager();
    private MyListView mListView;
    private game_adapter mAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_game,container,false);
        mListView = (MyListView)mContent.findViewById(R.id.game_list);
        mAdapter = new game_adapter(mActivity,R.layout.game_listview,dataManager.getGame());
        mListView.setAdapter(mAdapter);
        mListView.setFocusable(false);
        return mContent;
    }

    @Override
    protected void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Game t = dataManager.getGame().get(i);
                //Toast.makeText(mActivity, t.getNews_url(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mActivity,Activity_web.class);
                intent.putExtra("url", t.getGame_url());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
    }
}