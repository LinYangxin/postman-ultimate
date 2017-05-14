package com.example.panker.panker.bean;

import android.graphics.Bitmap;
import android.graphics.Color;



/**
 * Created by user on 2017/2/28.
 * 新闻实体类，包括新闻的标题，url和图片url以及概括
 */

public class News {
    private String tittle;
    private String news_url;
    private Bitmap news_img;
    private String sumarize;
    private String news_date;
    public News(String t, String u, String s,String a,Bitmap i) {
        setTittl(t);
        setNews_url(u);
        setSumarize(s);
        setNews_img(i);
        setNews_date(a);
    }

    public News() {
    }

    public String getNews_date() {
        return news_date;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittl(String tittle) {
        this.tittle = tittle;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public Bitmap getNews_img() {
        if(news_img==null){
            Bitmap bitmap = Bitmap.createBitmap(100 ,100,
                    Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.parseColor("#ce3d3a"));//填充颜色
            return bitmap;
        }
        return news_img;
    }

    public void setNews_img(Bitmap news_img) {
        this.news_img = news_img;
    }

    public String getSumarize() {
        return sumarize;
    }

    public void setSumarize(String sumarize) {
        this.sumarize = sumarize;
    }
}
