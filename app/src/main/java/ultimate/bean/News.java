package ultimate.bean;

import android.graphics.Bitmap;
import android.graphics.Color;



/**
 * Created by user on 2017/2/28.
 * 新闻实体类，包括新闻的标题，url和图片url以及概括
 */

public class News {
    private String title;
    private String url;
    private Bitmap img;
    private String sumarize;
    private String date;
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
        return date;
    }

    public void setNews_date(String date) {
        this.date = date;
    }

    public String getTittle() {
        return title;
    }

    public void setTittl(String title) {
        this.title = title;
    }

    public String getNews_url() {
        return url;
    }

    public void setNews_url(String url) {
        this.url = url;
    }

    public Bitmap getNews_img() {
        if(img==null){
            Bitmap bitmap = Bitmap.createBitmap(100 ,100,
                    Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.parseColor("#ce3d3a"));//填充颜色
            return bitmap;
        }
        return img;
    }

    public void setNews_img(Bitmap img) {
        this.img = img;
    }

    public String getSumarize() {
        return sumarize;
    }

    public void setSumarize(String sumarize) {
        this.sumarize = sumarize;
    }
}
