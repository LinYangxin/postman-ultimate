package ultimate.uilt.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by 兜兜 on 2017/4/5.
 */
public class SQLiteHelper extends SQLiteOpenHelper{

    public final static String DATABASE_NAME = "Postman";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME_News = "NewsFragment";
    public final static String TABLE_NAME_RollPages = "RollPages";
    public final static String TABLE_NAME_Games = "Games";
    public final static String TABLE_NAME_Users = "Users";
    public final static String TABLE_NAME_Skills = "Skills";
    //构造函数，建数据库
    public SQLiteHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建news表
        String sql_news = "CREATE TABLE " + TABLE_NAME_News
                + "(_id INTEGER PRIMARY KEY,"
                + " title VARCHAR(30),"
                + " summary VARCHAR(40),"
                + " url VARCHAR(30) NOT NULL,"
                + " picture BLOB NOT NULL)";
        db.execSQL(sql_news);
        //创建轮播图表
        String sql_rollpages = "CREATE TABLE " + TABLE_NAME_RollPages
                + "(_id INTEGER PRIMARY KEY,"
                + " url VARCHAR(30) NOT NULL,"
                + " picture BLOB NOT NULL)";
        db.execSQL(sql_rollpages);
        //创建比赛表
        String sql_games = "CREATE TABLE " + TABLE_NAME_Games
                + "(_id INTEGER PRIMARY KEY,"
                + " title VARCHAR(30) NOT NULL,"
                + " url VARCHAR(30) NOT NULL,"
                + " picture BLOB NOT NULL)";
        db.execSQL(sql_games);
        //创建用户表
        String sql_users = "CREATE TABLE " + TABLE_NAME_Users
                + "(telephone VARCHAR(20) PRIMARY KEY," //手机号码
                + " email VARCHAR(30),"  //电子邮箱
                + " nickname VARCHAR(20),"  //昵称
                + " isMan INTEGER,"  //性别
                + " isNew INTEGER,"  //新人
                + " head BLOB,"  //头像
                + " myself VARCHAR(30))";  //个人宣言
        db.execSQL(sql_users);
        //创建技能表
        String sql_skills = "CREATE TABLE " + TABLE_NAME_Skills
                + "(telephone VARCHAR(20) PRIMARY KEY,"
                + " team VARCHAR(15),"
                + " position VARCHAR(20),"
                + " throwing INTEGER,"
                + " catching INTEGER,"
                + " speed INTEGER,"
                + " offence INTEGER"
                + " defence INTEGER)";
        db.execSQL(sql_skills);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_news = "DROP TABLE IF EXISTS " + TABLE_NAME_News;
        String sql_rollpages = "DROP TABLE IF EXISTS " + TABLE_NAME_RollPages;
        String sql_games = "DROP TABLE IF EXISTS " + TABLE_NAME_Games;
        String sql_users = "DROP TABLE IF EXISTS " + TABLE_NAME_Users;
        String sql_skills = "DROP TABLE IF EXISTS " + TABLE_NAME_Skills;
        db.execSQL(sql_news);
        db.execSQL(sql_rollpages);
        db.execSQL(sql_games);
        db.execSQL(sql_users);
        db.execSQL(sql_skills);
        onCreate(db);

    }

    //获取某个表的游标
    public Cursor select(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

    public long insert_news(String title, String summary, String url, byte[] picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("summary", summary);
        cv.put("url", url);
        cv.put("picture",picture);
        long row = db.insert(TABLE_NAME_News, null, cv);
        return row;
    }

    public long insert_rollpages(String url, byte[] picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("url", url);
        cv.put("picture", picture);
        long row = db.insert(TABLE_NAME_RollPages, null, cv);
        return row;
    }
    public long insert_games(String url, byte[] picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("url", url);
        cv.put("picture", picture);
        long row = db.insert(TABLE_NAME_Games, null, cv);
        return row;
    }
    public long insert_users(String telephone, String email, String nickname, int isMan,int isNew, byte[] head,String myself) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("telephone", telephone);
        cv.put("email", email);
        cv.put("nickname", nickname);
        cv.put("isMan", isMan);
        cv.put("isNew", isNew);
        cv.put("head", head);
        cv.put("myself", myself);
        long row = db.insert(TABLE_NAME_Users, null, cv);
        return row;
    }
    public long insert_skills(String telephone, String team, String url,String position,int throwing,int catching,int speed, int offence,int defence ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("telephone", telephone);
        cv.put("team", team);
        cv.put("position", url);
        cv.put("throwing", throwing);
        cv.put("catching", catching);
        cv.put("speed", speed);
        cv.put("offence", offence);
        cv.put("defence", defence);
        long row = db.insert(TABLE_NAME_Skills, null, cv);
        return row;
    }

    //输入查询sql语句
    public Cursor query(String sql,String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        return cursor;
    }

    //输入删除语句
    public void delete(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }
    public void update(String sql){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(sql);
    }

}
