package ultimate.uilt.tools;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import ultimate.bean.Game;
import ultimate.bean.News;
import ultimate.bean.Rollpage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/3/1.
 * 数据管理类，存放从服务器获取的新闻和轮播图内容。
 */

public class DataManager {
    private static List<News> news = new ArrayList<>();//存放新闻实体的List
    private static List<Rollpage> roll = new ArrayList<>();//存放轮播图实体的List
    private static List<Game> game = new ArrayList<>();//存放比赛实体的List
    public static int getTimes = -1;
    public static long lastUpdateAt = 0;
    //构造函数
    public  DataManager(){
    }

    //初始化函数
    public void init(){
        getNewsData();
        getRollData();
        getGameData();

    }

    //从服务器获取比赛的json
    private static void getGameData(){

        if(!game.isEmpty())
            game.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Game");
        avQuery.orderByDescending("updatedAt");

        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject t : list) {
                        final String temp_name = t.getString("GameName");
                        final String temp_state = t.getString("GameState");
                        final String temp_url = t.getString("GameURL");
                        final String temp_date = t.getString("GameDate");
                        final int TOP = t.getInt("GameTop");
                        AVFile img = t.getAVFile("GamePost");
                        img.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                Game temp_game = new Game(temp_name, temp_url, temp_date, temp_state, bm);
                                if(TOP==1)
                                    game.add(0,temp_game);
                                else
                                    game.add(temp_game);
                            }
                        });
                    }
                }
            }
        });

    }
    //从服务器获取新闻的json
    public static boolean getNewsData(){
        lastUpdateAt = System.currentTimeMillis();
        getTimes++;
        if(getTimes==0 && !news.isEmpty())
            news.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("News");//使用AVQuery查询服务器中news表
        avQuery.orderByDescending("updatedAt");//以更新顺序排列
        avQuery.skip(getTimes * 8);
        avQuery.limit(8);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject t : list){
                        final String temp_tittle = t.getString("tittle");
                        final String temp_sum = t.getString("news_Sumarize");
                        final String temp_url = t.getString("news_URL");
                        final String temp_date = PankerHelper.Date2String(t.getDate("updatedAt"));
                        AVFile img = t.getAVFile("Pic_news");
                        img.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                Bitmap bm=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                News q= new News(temp_tittle,temp_url,temp_sum,temp_date,bm);
                                news.add(q);
                            }
                        });
                    }
                }
            }
        });
        return  true;
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
                        final String temp_url = t.getString("URL");
                        final String temp_url_pic = t.getString("url_pic");
                        Rollpage q= new Rollpage(temp_url,temp_url_pic);
                        roll.add(q);
                    }
                }
            }
        });

    }

//    //解析Json获取新闻   //参数1：json数据   //返回值String，获取的新闻URL
//    private static String[] getNewsInfo(String jsonData){
//        String [] back = {null,null,null,null};
//        try {
//            JSONObject json = new JSONObject(jsonData);
//            JSONObject array = json.getJSONObject("serverData");
//            back[2] = array.getString("news_Sumarize");
//            back[1] = array.getString("news_URL");
//            back[0] = array.getString("tittle");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return back;
//    }

    //解析Json获取轮播图  //参数：json数据  //返回值String[2]，获取的轮播图的URL以及图片url
//    private static String[] getRollInfo(String jsonData){
//        String [] back = {null,null};
//        try {
//            JSONObject json = new JSONObject(jsonData);
//            JSONObject array = json.getJSONObject("serverData");
//            back[0] = array.getString("URL");
//            back[1] = array.getString("url_pic");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return back;
//    }

    //获取新闻的List，公开
    public static List<News> getNews(){
        return news;
    }

    //获取轮播图的List，公开
    public static List<Rollpage> getRoll(){ return roll; }

    //获取比赛的list，公开

    public static List<Game> getGame() {
        return game;
    }


    //创建本地数据库
    //public static void Creat_dataBase(){
       // mdataBase.getWritableDatabase();
   // }
    //更新本地数据库
}
