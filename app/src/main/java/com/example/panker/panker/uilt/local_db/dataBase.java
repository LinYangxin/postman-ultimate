package com.example.panker.panker.uilt.local_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 兜儿 on 2017/3/18.
 */

public class dataBase extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREATE_User = "create table User ("
            +"telephone varchar primary key, "
            +"e_mail varchar, "
            +" nickmae varchar, "
            +"myself varchar, "
            +"isMan blob, "
            +"head blob, "
            +"position varchar)";
    public dataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
