package ultimate.bean;

import android.graphics.Bitmap;

/**
 * Created by user on 2017/4/27.
 */

public class Game {
    private String url;
    private Bitmap img;
    private String date;
    private String state;
    private String title;

    public Game(String t, String u, String d, String s, Bitmap b) {
        setTitle(t);
        setUrl(u);
        setDate(d);
        setImg(b);
        setState(s);
    }


    private void setTitle(String t) {
        title = t;
    }

    private void setUrl(String u) {
        url = u;
    }

    private void setDate(String d) {
        date = d;
    }

    private void setState(String s) {
        state = s;
    }

    private void setImg(Bitmap b) {
        img = b;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
