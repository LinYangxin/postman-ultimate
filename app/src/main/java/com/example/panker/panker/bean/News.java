package com.example.panker.panker.bean;

/**
 * Created by user on 2017/2/28.
 * 新闻实体类，包括新闻的标题，url和图片url以及概括
 */

public class News {
    private String tittle;
    private String news_url;
    private String news_img;
    private String sumarize;
    public News(String t,String u,String s){
        tittle=t;
        news_url = u;
        sumarize = s;
    }
    public News(){
    }
    public String getTittle(){
        return tittle;
    }

    public void setTittl(String tittle){
        this.tittle = tittle;
    }

    public String getNews_url() {
        return news_url;
    }
    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getNews_img() {
        return news_img;
    }

    public void setNews_img(String news_img) {
        this.news_img = news_img;
    }

    public String getSumarize() {
        return sumarize;
    }

    public void setSumarize(String sumarize) {
        this.sumarize = sumarize;
    }
}
