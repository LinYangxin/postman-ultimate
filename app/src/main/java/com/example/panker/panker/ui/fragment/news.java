package com.example.panker.panker.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.panker.panker.bean.News;
import com.example.panker.panker.bean.Rollpage;
import com.example.panker.panker.ui.adapter.news_adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.panker.panker.R;
import com.example.panker.panker.ui.activity.Activity_web;
import com.example.panker.panker.uilt.NewsListView.NewsListView;
import com.example.panker.panker.uilt.Tools.DataManager;
import com.example.panker.panker.uilt.rollviewpager.OnItemClickListener;
import com.example.panker.panker.uilt.rollviewpager.RollPagerView;
import com.example.panker.panker.uilt.rollviewpager.adapter.LoopPagerAdapter;
import com.example.panker.panker.uilt.rollviewpager.hintview.IconHintView;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;


/**
 * Created by Ivory on 2016/7/19.
 * 天才兜儿又来啦！！！
 * hahahaha
 */
public class news extends basefragment implements View.OnClickListener {
    private View mContent;
    private RollPagerView mLoopViewPager;
    private TestLoopAdapter mLoopAdapter;
    private Handler handler = new Handler();
    private static DataManager dataManager = new DataManager();
    private NewsListView mListView;
    private news_adapter mAdapter;
    private List<Rollpage> rollpages;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_news,container,false);
        mLoopViewPager = (RollPagerView) mContent.findViewById(R.id.loop_view_pager);
        mListView = (NewsListView)mContent.findViewById(R.id.news_list);
        mAdapter = new news_adapter(mActivity,R.layout.news_listview,dataManager.getNews());
        mListView.setAdapter(mAdapter);
        mListView.setFocusable(false);
        return mContent;
    }

    @Override
    protected void initEvent() {
        mLoopViewPager.setAdapter(mLoopAdapter = new TestLoopAdapter(mLoopViewPager));
        mLoopViewPager.setHintView(new IconHintView(mActivity,R.drawable.point_focus,R.drawable.point_normal));
        mLoopViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               // Toast.makeText(mActivity,"Item "+position+" clicked",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(mActivity,Activity_web.class);
                switch (position) {
                    case 0:
                        i.putExtra("url", rollpages.get(0).getUrl());
                        break;
                    case 1:
                        i.putExtra("url",rollpages.get(1).getUrl());
                        break;
                    case 2:
                        i.putExtra("url",rollpages.get(2).getUrl());
                        break;
                }
                startActivity(i);
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] c = new String[3];
                for(int i = 0;i<3;i++){
                    c[i]=new String(rollpages.get(i).getImgs());
                }
                mLoopAdapter.setImgs(c);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News t = dataManager.getNews().get(i);
                //Toast.makeText(mActivity, t.getNews_url(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mActivity,Activity_web.class);
                intent.putExtra("url", t.getNews_url());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void initData(){
        rollpages = dataManager.getRoll();
    }
    private class TestLoopAdapter extends LoopPagerAdapter {
        String[] imgs = new String[0];
        public void setImgs(String[] imgs){
            this.imgs = imgs;
            notifyDataSetChanged();
        }

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            Log.i("RollViewPager","getView:"+imgs[position]);

            ImageView view = new ImageView(container.getContext());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("RollViewPager","onClick");
                }
            });
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(mActivity)
                    .load(imgs[position])
                    .into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }
}