package ultimate.uilt.tools;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;

import ultimate.bean.Game;
import ultimate.bean.News;
import ultimate.bean.Rollpage;
import ultimate.bean.User;
import ultimate.ui.adapter.NewsAdapter;

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
    public static User user;

    //构造函数
    public DataManager() {
    }

    //初始化函数
    public static void init(Activity activity) {
        user = new User(activity);
        Log.i("DataManager.java", "初始化开始");
        getNewsData(null);
        getRollData();
        getGameData();
        Log.i("DataManager.java", "初始化结束");
        Log.i("DataManager.java", "轮播图数据初始化完成，一共有轮播图:" + roll.size() + "条");
        Log.i("DataManager.java", "新闻数据初始化完成,一共有新闻:" + news.size() + "条");
        Log.i("DataManager.java", "比赛数据初始化完成，一共有:" + game.size() + "条");
    }

    //从服务器获取比赛的json
    private static void getGameData() {
        Log.i("DataManager.java", "初始化比赛数据");
        if (!game.isEmpty())
            game.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Game");
        avQuery.orderByDescending("updatedAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                Log.i("DataManager.java", "从服务器中获取比赛数据");
                if (e == null) {
                    Log.i("DataManager.java", "获取比赛数据成功，开始解json");
                    for (AVObject t : list) {
                        final String temp_name = t.getString("GameName");
                        final String temp_state = t.getString("GameState");
                        final String temp_url = t.getString("GameURL");
                        final String temp_date = t.getString("GameDate");
                        final int TOP = t.getInt("GameTop");
                        AVFile img = t.getAVFile("GamePost");
                        Log.i("DataManager.java", "从服务器中获取比赛图片");
                        img.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Log.i("DataManager.java", "获取比赛图片成功");
                                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    Game temp_game = new Game(temp_name, temp_url, temp_date, temp_state, bm);
                                    if (TOP == 1)
                                        game.add(0, temp_game);
                                    else
                                        game.add(temp_game);
                                } else {
                                    Log.e("DataManager.java", "获取比赛图片失败，实体生成不成功，原因为:" + e.getMessage());
                                }
                            }
                        });
                    }

                } else {
                    Log.e("DataManager.java", "比赛数据初始化失败,原因为：" + e.getMessage());
                }
            }
        });

    }

    //从服务器获取新闻的json
    public static boolean getNewsData(final NewsAdapter mAdapter) {
        Log.i("DataManager.java", "初始化新闻数据");
        lastUpdateAt = System.currentTimeMillis();
        getTimes++;
        if (getTimes == 0 && !news.isEmpty())
            news.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("news");//使用AVQuery查询服务器中news表
        avQuery.orderByDescending("updatedAt");//以更新顺序排列
        avQuery.skip(getTimes * 8);
        avQuery.limit(8);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                Log.i("DataManager.java", "从服务器中获取新闻数据");
                if (e == null) {
                    Log.i("DataManager.java", "获取成功，开始解json");
                    for (AVObject t : list) {
                        final String temp_tittle = t.getString("tittle");
                        final String temp_sum = t.getString("news_Sumarize");
                        final String temp_url = t.getString("news_URL");
                        final String temp_date = PostmanHelper.Date2String(t.getDate("updatedAt"));
                        AVFile img = t.getAVFile("Pic_news");
                        Log.i("DataManager.java", "从服务器获取新闻的图片");
                        img.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    News q = new News(temp_tittle, temp_url, temp_sum, temp_date, bm);
                                    news.add(q);
                                    if(mAdapter != null) {
                                        mAdapter.notifyDataSetChanged();
                                        Log.e("刷新DataManager.java", "notifyDataSetChanged()");
                                    }
                                } else {
                                    Log.e("DataManager.java", "获取新闻图片失败，实体生成不成功,原因为:" + PostmanHelper.getCodeFromServer(e));
                                }
                            }
                        });
                    }

                } else {
                    Log.e("DataManager.java", "新闻初始化失败，原因为：" + e.getMessage());
                }
            }
        });
        return true;
    }

    //从服务器获取轮播图的json
    private static void getRollData() {
        Log.i("DataManager.java", "初始化轮播图数据");
        AVQuery<AVObject> avQuery = new AVQuery<>("rollview");
        avQuery.orderByDescending("updatedAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                Log.i("DataManager.java", "从服务器中获取轮播图数据");
                if (e == null) {
                    Log.i("DataManager.java", "获取成功，正在解json");
                    for (AVObject t : list) {
                        final String temp_url = t.getString("URL");
                        final String temp_url_pic = t.getString("url_pic");
                        Rollpage q = new Rollpage(temp_url, temp_url_pic);
                        roll.add(q);
                    }

                } else {
                    Log.i("DataManager.java", "轮播图数据初始化失败，原因为：" + PostmanHelper.getCodeFromServer(e));
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
    public static List<News> getNews() {
        return news;
    }

    //获取轮播图的List，公开
    public static List<Rollpage> getRoll() {
        return roll;
    }

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
