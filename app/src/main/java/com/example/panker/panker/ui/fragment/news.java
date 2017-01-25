package com.example.panker.panker.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.Toast;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.example.panker.panker.R;
import com.example.panker.panker.ui.activity.Activity_main;
import com.example.panker.panker.ui.activity.Activity_web;
import com.example.panker.panker.uilt.Tools.NetUtils;
import com.example.panker.panker.uilt.Tools.TittleManager;
import com.example.panker.panker.uilt.rollviewpager.OnItemClickListener;
import com.example.panker.panker.uilt.rollviewpager.RollPagerView;
import com.example.panker.panker.uilt.rollviewpager.adapter.LoopPagerAdapter;
import com.example.panker.panker.uilt.rollviewpager.hintview.IconHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
    final String []ObjectId=new String[]{"587dc3d961ff4b00650b4f12","587dc3d5128fe1005703d300","587dc3d961ff4b00650b4f12"};
    final String[] imgs  = new String[3];
    final String[] url = new String[3];
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent=inflater.inflate(R.layout.fragment_news,container,false);
        mLoopViewPager= (RollPagerView) mContent.findViewById(R.id.loop_view_pager);
        return mContent;
    }

    @Override
    protected void initEvent() {
        mLoopViewPager.setPlayDelay(1000);
        mLoopViewPager.setAdapter(mLoopAdapter = new TestLoopAdapter(mLoopViewPager));
        mLoopViewPager.setHintView(new IconHintView(mActivity,R.drawable.point_focus,R.drawable.point_normal));
        mLoopViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mActivity,"Item "+position+" clicked",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(mActivity,Activity_web.class);
                switch (position) {
                    case 0:
                       // i.putExtra("url", "http://mp.weixin.qq.com/s/t0uWfNgb_KZdLmP08LRxvg");
                        i.putExtra("url",url[0]);
                        break;
                    case 1:
                       // i.putExtra("url", "http://mp.weixin.qq.com/s/0GTUed75a5vQJW5gNMfQYA");
                        i.putExtra("url",url[1]);
                        break;
                    case 2:
                        //i.putExtra("url", "http://mp.weixin.qq.com/s/Zv_8mvf5lA7ZvIVQu6kMPQ");
                        i.putExtra("url",url[2]);
                        break;
                }
                startActivity(i);
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                mLoopAdapter.setImgs(imgs);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void initData(){
        for(int i=0;i<3;i++) {
            final AVObject tmp = AVObject.createWithoutData("rollview", ObjectId[i]);
            final int pos = i;
            tmp.fetchInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    imgs[pos] = tmp.getString("url_pic");
                    url[pos] = tmp.getString("URL");
                    Log.i("qqqqqqqqqqqqq",imgs[pos]);
                    Log.i("wwwwwwwwwwwww",url[pos]);
                }
            });
        }
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
                    // .placeholder(R.drawable.img4)
                    // .error(R.drawable.img1)
                    .into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }
}