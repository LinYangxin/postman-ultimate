package com.example.panker.panker.bean;

import android.graphics.Bitmap;

/**
 * Created by user on 2017/4/27.
 */

public class Game {
    private String game_url;
    private Bitmap game_img;
    private String game_date;
    private String game_state;
    private String game_tittle;

    public Game(String t, String u, String d, String s, Bitmap b) {
        setGame_tittle(t);
        setGame_url(u);
        setGame_date(d);
        setGame_img(b);
        setGame_state(s);
    }


    private void setGame_tittle(String t) {
        game_tittle = t;
    }

    private void setGame_url(String u) {
        game_url = u;
    }

    private void setGame_date(String d) {
        game_date = d;
    }

    private void setGame_state(String s) {
        game_state = s;
    }

    private void setGame_img(Bitmap b) {
        game_img = b;
    }

    public Bitmap getGame_img() {
        return game_img;
    }

    public String getGame_date() {
        return game_date;
    }

    public String getGame_state() {
        return game_state;
    }

    public String getGame_url() {
        return game_url;
    }

    public String getGame_tittle() {
        return game_tittle;
    }
}
