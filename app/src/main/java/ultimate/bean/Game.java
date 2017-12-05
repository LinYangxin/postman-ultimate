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
        setGame_tittle(t);
        setGame_url(u);
        setGame_date(d);
        setGame_img(b);
        setGame_state(s);
    }


    private void setGame_tittle(String t) {
        title = t;
    }

    private void setGame_url(String u) {
        url = u;
    }

    private void setGame_date(String d) {
        date = d;
    }

    private void setGame_state(String s) {
        state = s;
    }

    private void setGame_img(Bitmap b) {
        img = b;
    }

    public Bitmap getGame_img() {
        return img;
    }

    public String getGame_date() {
        return date;
    }

    public String getGame_state() {
        return state;
    }

    public String getGame_url() {
        return url;
    }

    public String getGame_tittle() {
        return title;
    }
}
