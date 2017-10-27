package ultimate.bean;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.example.panker.ultimate.R;

/**
 * Created by user on 2016/9/10.
 */
public class User {
    private static String Phonenumber, Email, Nickname, Myself, Team, Position;
    private static float O, D, Speed, Catching, Throwing;
    private static Bitmap head, background;
    private static boolean isMan, hasHead, hasBackground;//isNew用以判断是否加载头像
    private static AVUser myUser;

    public User() {
    }

    public User(Activity activity) {
        init();
        if (hasHead) {
            myUser.getAVFile("head").getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    if (e == null) {
                        setHead(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    }
                }
            });
        } else {
            Resources res = activity.getResources();
            setHead(BitmapFactory.decodeResource(res, R.drawable.head));
        }
        if (hasBackground) {
            myUser.getAVFile("bg").getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    if (e == null) {
                        setBackground(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    }
                }
            });
        } else {
            Resources res = activity.getResources();
            setBackground(BitmapFactory.decodeResource(res, R.drawable.test));
        }
    }

    public void init() {
        myUser = AVUser.getCurrentUser();
        Myself = myUser.getString("myself");
        Phonenumber = myUser.getMobilePhoneNumber();
        Email = myUser.getEmail();
        Nickname = myUser.getString("nickname");
        Team = myUser.getString("team");
        Position = myUser.getString("position");
        O = (float) myUser.getDouble("O");
        D = (float) myUser.getDouble("D");
        Speed = (float) myUser.getDouble("speed");
        Catching = (float) myUser.getDouble("catching");
        Throwing = (float) myUser.getDouble("throwing");
        isMan = myUser.getBoolean("isMan");
        hasHead = myUser.getBoolean("hasHead");
        hasBackground = myUser.getBoolean("hasBackground");
    }

    public static AVUser getMyUser() {
        return myUser;
    }

    //    public static void setHasBackground(boolean hasBackground) {
//        User.hasBackground = hasBackground;
//    }
//
//    public static boolean getHasBackground() {
//        return hasBackground;
//    }
//
    public static Bitmap getBackground() {
        return background;
    }

    public static void setBackground(Bitmap background) {
        User.background = background;
    }

    public static boolean getSex() {
        return isMan;
    }

    //    public static boolean getFlag(){
//        return isNew;
//    }
    public static String getPhonenumber() {
        return Phonenumber;
    }

    public static String getEmail() {
        return Email;
    }

    public static String getMyself() {
        return Myself;
    }

    public static String getNickname() {
        return Nickname;
    }

    public static String getPosition() {
        return Position;
    }

    public static String getTeam() {
        return Team;
    }

    public static float getCatching() {
        return Catching;
    }

    public static float getD() {
        return D;
    }

    public static float getO() {
        return O;
    }

    public static float getSpeed() {
        return Speed;
    }

    public static float getThrowing() {
        return Throwing;
    }

    public static Bitmap getHead() {
        if (head == null) {
            Bitmap bitmap = Bitmap.createBitmap(50, 50,
                    Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.parseColor("#FF0000"));//填充颜色
            return bitmap;
        }
        return head;
    }

    public static void setCatching(float catching) {
        Catching = catching;
    }

    public static void setD(float d) {
        D = d;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static void setHead(Bitmap head) {
        User.head = head;
    }

    public static void setMyself(String myself) {
        Myself = myself;
    }

    public static void setNickname(String nickname) {
        Nickname = nickname;
    }

    public static void setO(float o) {
        O = o;
    }

    public static void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public static void setPosition(String position) {
        Position = position;
    }

    public static void setSpeed(float speed) {
        Speed = speed;
    }

    public static void setTeam(String team) {
        Team = team;
    }

    public static void setThrowing(float throwing) {
        Throwing = throwing;
    }

    public static void setHasHead(boolean t) {
        hasHead = t;
    }

    public static void setHasBackground(boolean t) {
        hasBackground = t;
    }

    public static void setSex(boolean t) {
        isMan = t;
    }
}
