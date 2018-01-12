package ultimate.bean;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.example.postman.ultimate.R;

import ultimate.uilt.tools.PostmanHelper;

/**
 * Created by user on 2016/9/10.
 */
public class User {
    private String phoneNumber, email, nickname, myself, team, position;
    private float offensive, defense, speed, catching, throwing;
    private Bitmap head, background;
    private boolean isMan, hasHead, hasBackground;//isNew用以判断是否加载头像
    private AVUser myUser;

    public User() {
//        init();
    }

    public User(Activity activity) {
        init();
        if (hasHead) {
            Log.i("User.java", "从服务器获取头像");
            myUser.getAVFile("head").getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    if (e == null) {
                        Log.d("User.java", "获取头像成功");
                        setHead(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    } else {
                        Log.e("User.java", "获取头像失败，原因为：" + PostmanHelper.getCodeFromServer(e));
                    }
                }
            });
        } else {
            Log.i("User.java", "使用默认头像");
            Resources res = activity.getResources();
            setHead(BitmapFactory.decodeResource(res, R.drawable.head));
        }
        if (hasBackground) {
            Log.i("User.java", "从服务器获取背景图片");
            myUser.getAVFile("bg").getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    if (e == null) {
                        Log.i("User.java", "获取背景图片成功");
                        setBackground(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    } else {
                        Log.e("User.java", "获取背景图片失败，原因为：" + PostmanHelper.getCodeFromServer(e));
                    }
                }
            });
        } else {
            Log.i("User.java", "使用默认背景图片");
            Resources res = activity.getResources();
            setBackground(BitmapFactory.decodeResource(res, R.drawable.test));
        }
    }

    public void init() {
        Log.i("User.java", "初始化用户");
        myUser = AVUser.getCurrentUser();
        myself = myUser.getString("myself");
        phoneNumber = myUser.getMobilePhoneNumber();
        email = myUser.getEmail();
        nickname = myUser.getString("nickname");
        team = myUser.getString("team");
        position = myUser.getString("position");
        offensive = (float) myUser.getDouble("O");
        defense = (float) myUser.getDouble("D");
        speed = (float) myUser.getDouble("speed");
        catching = (float) myUser.getDouble("catching");
        throwing = (float) myUser.getDouble("throwing");
        isMan = myUser.getBoolean("isMan");
        hasHead = myUser.getBoolean("hasHead");
        hasBackground = myUser.getBoolean("hasBackground");
    }

    public AVUser getMyUser() {
        return myUser;
    }

    public Bitmap getBackground() {
        return background;
    }

    public void setBackground(Bitmap background) {
        this.background = background;
    }

    public String getSex() {
        if (isMan)
            return "男";
        else
            return "女";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMyself() {
        return myself;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }

    public float getCatching() {
        return catching;
    }

    public float getD() {
        return defense;
    }

    public float getO() {
        return offensive;
    }

    public float getSpeed() {
        return speed;
    }

    public float getThrowing() {
        return throwing;
    }

    public Bitmap getHead() {
        if (head == null) {
            Bitmap bitmap = Bitmap.createBitmap(50, 50,
                    Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.parseColor("#FF0000"));//填充颜色
            return bitmap;
        }
        return head;
    }

    public void setCatching(float catching) {
        this.catching = catching;
    }

    public void setD(float defense) {
        this.defense = defense;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }

    public void setMyself(String myself) {
        this.myself = myself;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setO(float offensive) {
        this.offensive = offensive;
    }

    public void setPhonenumber(String phonenumber) {
        phoneNumber = phonenumber;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setThrowing(float throwing) {
        this.throwing = throwing;
    }

    public void setHasHead(boolean t) {
        hasHead = t;
    }

    public void setHasBackground(boolean t) {
        hasBackground = t;
    }

    public void setSex(boolean t) {
        isMan = t;
    }
}
