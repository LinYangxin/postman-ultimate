package com.example.panker.panker.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.example.panker.panker.R;
//import com.example.panker.panker.uilt.Tools.Head;
import com.example.panker.panker.uilt.Tools.TittleManager;
import com.example.panker.panker.bean.User;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by user on 2016/8/24......
 **/
public class Activity_me_data extends Activity implements View.OnClickListener {
    private TittleManager tittleManager;
    //private AVUser myUser;
    private String[] text = new String[7];
    private TextView tv_0, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6;
    private RelativeLayout rl_head, rl_nickname, rl_myself, rl_team, rl_sex, rl_position, rl_phone, rl_email;
    private final int REQUEST_NICKNAME = 0, REQUEST_PHONE = 1, REQUEST_EMAIL = 2, REQUEST_TEAM = 3, REQUEST_MYSELF = 6, NOTHING = 999;
    private final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1, REQUEST_CODE_CAMERA = 2;//用以动态获取权限
    private final int REQUEST_BY_CAMERA = 110, REQUEST_BY_GALLERY = 111, REQUEST_BY_CROP = 112;
    private ImageView head;
    //private Head new_head;
    protected static Uri tempUri;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        user = new User();
       // new_head = new Head(0);
        for (int i = 0; i < 7; i++) {
            text[i] = new String();
        }
        text[0] = user.getNickname();
        text[1] = user.getPhonenumber();
        text[2] = user.getEmail();
        text[3] = user.getTeam();
        if (user.getSex()) {
            text[4] = "男";
        } else {
            text[4] = "女";
        }
        text[5] = user.getPosition();
        text[6] = user.getMyself();
    }

    private void initView() {
        tittleManager = new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_BACK, "个人资料");
        head = (ImageView) findViewById(R.id.head);
        tv_0 = (TextView) findViewById(R.id.tv_0);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_0.setText(text[0]);
        tv_1.setText(text[1]);
        tv_2.setText(text[2]);
        tv_3.setText(text[3]);
        tv_4.setText(text[4]);
        tv_5.setText(text[5]);
        tv_6.setText(text[6]);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        rl_nickname = (RelativeLayout) findViewById(R.id.rl_nickname);
        rl_myself = (RelativeLayout) findViewById(R.id.rl_myself);
        rl_team = (RelativeLayout) findViewById(R.id.rl_team);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_position = (RelativeLayout) findViewById(R.id.rl_position);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_email = (RelativeLayout) findViewById(R.id.rl_email);
        head.setImageBitmap(user.getHead());
    }

    private void initEvent() {
        rl_head.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_myself.setOnClickListener(this);
        rl_team.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_position.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_email.setOnClickListener(this);
        tittleManager.setLeftTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void SetHeadFromCamera() {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/Panker");
            if (!dir.exists()) dir.mkdirs();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(dir, "head.png");//localTempImgDir和localTempImageFileName是自己定义的名字
            Uri u = Uri.fromFile(f);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
            startActivityForResult(intent, REQUEST_BY_CAMERA);
        } catch (ActivityNotFoundException e) {

            Toast.makeText(Activity_me_data.this, "没有找到储存目录", Toast.LENGTH_LONG).show();
        }
    }

    private void SetHeadFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_BY_GALLERY);
    }

    public void GetPomitssion() {
        new AlertDialog.Builder(this).setTitle("设置头像").setIcon(
                android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                new String[]{"拍照", "图库"}, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (Build.VERSION.SDK_INT >= 23) {
                                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(Activity_me_data.this, Manifest.permission.CAMERA);
                                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(Activity_me_data.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                                        return;
                                    } else {
                                        SetHeadFromCamera();  // 开启图片选择器
                                    }
                                } else {
                                    SetHeadFromCamera();   // 开启图片选择器
                                }
                                dialog.dismiss();
                                break;
                            case 1:
                                if (Build.VERSION.SDK_INT >= 23) {
                                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(Activity_me_data.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(Activity_me_data.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
                                        return;
                                    } else {
                                        SetHeadFromGallery();  // 开启图片选择器
                                    }
                                } else {
                                    SetHeadFromGallery();   // 开启图片选择器
                                }
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_head:
                GetPomitssion();
                break;
            case R.id.rl_nickname:
                intent = new Intent(Activity_me_data.this, Activity_me_data_nickname.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_phone:
                intent = new Intent(Activity_me_data.this, Activity_me_data_verifyPhone.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_myself:
                intent = new Intent(Activity_me_data.this, Activity_me_data_myself.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_team:
                intent = new Intent(Activity_me_data.this, Activity_me_data_team.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_email:
                intent = new Intent(Activity_me_data.this, Activity_me_data_email.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_sex:
                setSex();
                break;
            case R.id.rl_position:
                setPosition();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                switch (resultCode) {
                    case REQUEST_NICKNAME:
                        text[0] = user.getNickname();
                        tv_0.setText(text[0]);
                        break;
                    case REQUEST_PHONE:
                        text[1] = user.getPhonenumber();
                        tv_1.setText(text[1]);
                        break;
                    case REQUEST_EMAIL:
                        text[2] = user.getEmail();
                        tv_2.setText(text[2]);
                        break;
                    case REQUEST_TEAM:
                        text[3] = user.getTeam();
                        tv_3.setText(text[3]);
                        break;
                    case REQUEST_MYSELF:
                        text[6] = user.getMyself();
                        tv_6.setText(text[6]);
                        break;
                    case NOTHING:
                        break;
                }
                break;
            case REQUEST_BY_CAMERA:
                if(resultCode==RESULT_OK){
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/" + "Panker" + "/" + "head.png");
                    try {
                        Uri u = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
                                        f.getAbsolutePath(), null, null));
                        //u就是拍摄获得的原始图片的uri，剩下的你想干神马坏事请便……
                        startImageZoom(u);
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_BY_GALLERY:
                if (data == null) {
                    break;
                } else {
                    startImageZoom(data.getData());
                }
                break;
            case REQUEST_BY_CROP:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    final Bitmap bm = extras.getParcelable("data");
                    head.setImageBitmap(bm);
                    byte[] img_data;
//压缩成PNG
                    final ByteArrayOutputStream os = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, os);
//得到二进制数据*/
                    img_data = os.toByteArray();

                    AVFile file = new AVFile("head.png", img_data);
                    user.getMyUser().put("head", file);
                    user.getMyUser().put("newSign", false);
                    user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            user.setHead(bm);
                            user.setFlag(false);
                            Toast.makeText(Activity_me_data.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        Activity_me_data.this.setResult(1);
        Activity_me_data.this.finish();
    }

    private void setSex() {
        int selected;
        if (user.getSex())
            selected = 0;
        else
            selected = 1;
        new AlertDialog.Builder(this).setTitle("性别").setSingleChoiceItems(
                new String[]{"男", "女"}, selected,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                user.getMyUser().put("isMan", true);
                                user.getMyUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            text[4] = "男";
                                            tv_4.setText(text[4]);
                                            user.setSex(true);
                                            Toast.makeText(Activity_me_data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Activity_me_data.this, "失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                                break;
                            case 1:
                                user.getMyUser().put("isMan", false);
                                user.getMyUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            text[4] = "女";
                                            tv_4.setText(text[4]);
                                            user.setSex(false);
                                            Toast.makeText(Activity_me_data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Activity_me_data.this, "失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
    }

    private void setPosition() {
        int p;
        switch (user.getPosition()) {
            case "Cutter":
                p = 0;
                break;
            case "Handler":
                p = 1;
                break;
            case "I don't know":
                p = 2;
                break;
            case "Cutter & Handler":
                p = 3;
                break;
            default:
                p = 0;
                break;
        }
        new AlertDialog.Builder(this).setTitle("场上位置").setSingleChoiceItems(
                new String[]{"Cutter", "Handler", "I don't know", "Cutter & Handler"}, p,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                user.getMyUser().put("position", "Cutter");
                                user.getMyUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            text[5] = "Cutter";
                                            tv_5.setText(text[5]);
                                            user.setPosition(text[5]);
                                            Toast.makeText(Activity_me_data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Activity_me_data.this, "失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                                break;
                            case 1:
                                user.getMyUser().put("position", "Handler");
                                user.getMyUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            text[5] = "Handler";
                                            tv_5.setText(text[5]);
                                            user.setPosition(text[5]);
                                            Toast.makeText(Activity_me_data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Activity_me_data.this, "失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                                break;
                            case 2:
                                user.getMyUser().put("position", "I don't know");
                                user.getMyUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            text[5] = "I don't know";
                                            tv_5.setText(text[5]);
                                            user.setPosition(text[5]);
                                            Toast.makeText(Activity_me_data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Activity_me_data.this, "失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                                break;
                            case 3:
                                user.getMyUser().put("position", "Cutter & Handler");
                                user.getMyUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            text[5] = "Cutter & Handler";
                                            tv_5.setText(text[5]);
                                            user.setPosition(text[5]);
                                            Toast.makeText(Activity_me_data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Activity_me_data.this, "失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
    }

    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_BY_CROP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SetHeadFromGallery();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SetHeadFromCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
