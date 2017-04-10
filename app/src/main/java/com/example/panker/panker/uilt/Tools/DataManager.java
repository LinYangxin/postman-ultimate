package com.example.panker.panker.uilt.Tools;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.panker.panker.bean.News;
import com.example.panker.panker.bean.Rollpage;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/3/1.
 * 数据管理类，存放从服务器获取的新闻和轮播图内容。
 */

public class DataManager {
    private static List<News> news = new ArrayList<>();//存放新闻实体的List
    private static List<Rollpage> roll = new ArrayList<>();//存放轮播图实体的List
    //构造函数
    public  DataManager(){
    }

    //初始化函数
    public void init(){
        getNewsData();
        getRollData();
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
                        String [] temp = getNewsInfo(String.valueOf(t));
                        News q= new News(temp[0],temp[1],temp[2]);
                        news.add(q);
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
        String [] back = {null,null,null};
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONObject array = json.getJSONObject("serverData");
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
