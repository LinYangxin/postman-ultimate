package com.example.panker.panker.uilt.Tools;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.example.panker.panker.bean.News;
import com.example.panker.panker.bean.Rollpage;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/3/1.
 * 数据管理类，存放从服务器获取的新闻和轮播图内容。
 */

public class DataManager {
    private static List<News> news = new ArrayList<>();//存放新闻实体的List
    private static List<Rollpage> roll = new ArrayList<>();//存放轮播图实体的List
    private static List<String> img_url = new ArrayList<>();
    //构造函数
    public  DataManager(){
    }

    //初始化函数
    public void init(){
        getNewsData();
        getRollData();
        getImg();
    }
    private void getImg(){

    }
    //从服务器获取新闻的json
    private static void getNewsData(){
        if(!news.isEmpty())
            news.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("news");
        avQuery.orderByDescending("updatedAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject t : list){
                        final String temp_info = String.valueOf(t);
                        AVFile img = t.getAVFile("Pic_news");
                        img.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                String [] temp = getNewsInfo(temp_info);
                                Bitmap bm=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                News q= new News(temp[0],temp[1],temp[2],bm);
                                news.add(q);
                            }
                        });
                       // Log.e("news",temp[0]+"/"+temp[1]+"/"+temp[2]+"/"+temp[3])
                    }
                }
            }
        });
    }

    //从服务器获取轮播图的json
    private static void getRollData(){
        AVQuery<AVObject> avQuery = new AVQuery<>("rollview");
        avQuery.orderByDescending("updatedAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject t : list){
                        String [] temp = getRollInfo(String.valueOf(t));
                        Rollpage q= new Rollpage(temp[0],temp[1]);
                        roll.add(q);
                    }
                }
            }
        });

    }

    //解析Json获取新闻   //参数1：json数据   //返回值String，获取的新闻URL
    private static String[] getNewsInfo(String jsonData){
        String [] back = {null,null,null,null};
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONObject array = json.getJSONObject("serverData");
            back[3] = "https://dn-7v4zq0wj.qbox.me/eb29e518e99676a85456.png";
            img_url.add(back[3]);
            back[2] = array.getString("news_Sumarize");
            back[1] = array.getString("news_URL");
            back[0] = array.getString("tittle");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return back;
    }

    //解析Json获取轮播图  //参数：json数据  //返回值String[2]，获取的轮播图的URL以及图片url
    private static String[] getRollInfo(String jsonData){
        String [] back = {null,null};
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONObject array = json.getJSONObject("serverData");
            back[0] = array.getString("URL");
            back[1] = array.getString("url_pic");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return back;
    }

    //获取新闻的List，公开
    public static List<News> getNews(){
        return news;
    }

    //获取轮播图的List，公开
    public static List<Rollpage> getRoll(){ return roll; }

    //创建本地数据库
    //public static void Creat_dataBase(){
       // mdataBase.getWritableDatabase();
   // }
    //更新本地数据库
}
