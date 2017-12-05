package ultimate.ui.activity;

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
import com.example.postman.ultimate.R;
//import com.example.ultimate.ultimate.uilt.Tools.Head;
import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.tools.TitleManager;
import ultimate.bean.User;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by user on 2016/8/24......
 **/
public class Data extends Activity implements View.OnClickListener {
    private TitleManager titleManager;
    //private AVUser myUser;
    private String[] text = new String[7];
    private TextView tvNickname, tvPhoneNumber, tvEmail, tvTeam, tvSex, tvPosition, tvMyself;
    private RelativeLayout rlHead, rlNickname, rlMyself, rlTeam, rlSex, rlPosition, rlPhone, rlEmail,rlPower;
    private ImageView head;
    //private Head new_head;
    protected static Uri tempUri;
    //private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_mydata);
        initData();
        initView();
        initEvent();

    }

    private void initData() {
       // new_head = new Head(0);
        for (int i = 0; i < 7; i++) {
            text[i] = new String();
        }
        text[0] = DataManager.user.getNickname();
        text[1] = DataManager.user.getPhoneNumber();
        text[2] = DataManager.user.getEmail();
        text[3] = DataManager.user.getTeam();
        text[4] = DataManager.user.getSex();
        text[5] = DataManager.user.getPosition();
        text[6] = DataManager.user.getMyself();
    }

    private void initView() {
        titleManager = new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.ONLY_BACK, "个人资料");
        head = (ImageView) findViewById(R.id.head);
        tvNickname = (TextView) findViewById(R.id.tvNickname);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvTeam = (TextView) findViewById(R.id.tvTeam);
        tvSex = (TextView) findViewById(R.id.tvSex);
        tvPosition = (TextView) findViewById(R.id.tvPositon);
        tvMyself = (TextView) findViewById(R.id.tvMyself);
        tvNickname.setText(text[0]);
        tvPhoneNumber.setText(text[1]);
        tvEmail.setText(text[2]);
        tvTeam.setText(text[3]);
        tvSex.setText(text[4]);
        tvPosition.setText(text[5]);
        tvMyself.setText(text[6]);
        rlHead = (RelativeLayout) findViewById(R.id.rlHead);
        rlNickname = (RelativeLayout) findViewById(R.id.rlNickname);
        rlMyself = (RelativeLayout) findViewById(R.id.rlMyself);
        rlTeam = (RelativeLayout) findViewById(R.id.rlTeam);
        rlSex = (RelativeLayout) findViewById(R.id.rlSex);
        rlPosition = (RelativeLayout) findViewById(R.id.rlPosition);
        rlPhone = (RelativeLayout) findViewById(R.id.rlPhoneNumber);
        rlEmail = (RelativeLayout) findViewById(R.id.rlEmail);
        rlPower = (RelativeLayout) findViewById(R.id.rlPower);
        head.setImageBitmap(DataManager.user.getHead());
    }

    private void initEvent() {
        rlHead.setOnClickListener(this);
        rlNickname.setOnClickListener(this);
        rlMyself.setOnClickListener(this);
        rlTeam.setOnClickListener(this);
        rlSex.setOnClickListener(this);
        rlPosition.setOnClickListener(this);
        rlPhone.setOnClickListener(this);
        rlEmail.setOnClickListener(this);
        rlPower.setOnClickListener(this);
        titleManager.setLeftTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void SetHeadFromCamera() {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/Ultimate");
            if (!dir.exists()) dir.mkdirs();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(dir, "head.png");//localTempImgDir和localTempImageFileName是自己定义的名字
            Uri u = Uri.fromFile(f);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
            startActivityForResult(intent, PostmanHelper.REQUEST_BY_CAMERA);
        } catch (ActivityNotFoundException e) {

            Toast.makeText(Data.this, "没有找到储存目录", Toast.LENGTH_LONG).show();
        }
    }

    private void SetHeadFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PostmanHelper.REQUEST_BY_GALLERY);
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
                                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(Data.this, Manifest.permission.CAMERA);
                                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(Data.this, new String[]{Manifest.permission.CAMERA}, PostmanHelper.REQUEST_CODE_CAMERA);
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
                                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(Data.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(Data.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PostmanHelper.REQUEST_CODE_READ_EXTERNAL_STORAGE);
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
            case R.id.rlHead:
                GetPomitssion();
                break;
            case R.id.rlNickname:
                intent = new Intent(Data.this, Nickname.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rlPhoneNumber:
                intent = new Intent(Data.this, ResetPhone.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rlMyself:
                intent = new Intent(Data.this, Myself.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rlTeam:
                intent = new Intent(Data.this, Team.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rlEmail:
                intent = new Intent(Data.this, Email.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rlSex:
                setSex();
                break;
            case R.id.rlPosition:
                setPosition();
                break;
            case R.id.rlPower:
                intent = new Intent(this, Power.class);
                startActivity(intent);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                switch (resultCode) {
                    case PostmanHelper.REQUEST_NICKNAME:
                        text[0] = DataManager.user.getNickname();
                        tvNickname.setText(text[0]);
                        break;
                    case PostmanHelper.REQUEST_PHONE:
                        text[1] = DataManager.user.getPhoneNumber();
                        tvPhoneNumber.setText(text[1]);
                        break;
                    case PostmanHelper.REQUEST_EMAIL:
                        text[2] = DataManager.user.getEmail();
                        tvEmail.setText(text[2]);
                        break;
                    case PostmanHelper.REQUEST_TEAM:
                        text[3] = DataManager.user.getTeam();
                        tvTeam.setText(text[3]);
                        break;
                    case PostmanHelper.REQUEST_MYSELF:
                        text[6] = DataManager.user.getMyself();
                        tvMyself.setText(text[6]);
                        break;
                    case PostmanHelper.NOTHING:
                        break;
                }
                break;
            case PostmanHelper.REQUEST_BY_CAMERA:
                if(resultCode==RESULT_OK){
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/" + "Postman" + "/" + "head.png");
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
            case PostmanHelper.REQUEST_BY_GALLERY:
                if (data == null) {
                    break;
                } else {
                    startImageZoom(data.getData());
                }
                break;
            case PostmanHelper.REQUEST_BY_CROP:
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
                    DataManager.user.getMyUser().put("head", file);
                    DataManager.user.getMyUser().put("hasHead", true);
                    DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            DataManager.user.setHead(bm);
                            DataManager.user.setHasHead(true);
                            Toast.makeText(Data.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        Data.this.setResult(1);
        Data.this.finish();
    }

    private void setSex() {
        int selected;
        if (DataManager.user.getSex()=="男")
            selected = 0;
        else
            selected = 1;
        new AlertDialog.Builder(this).setTitle("性别").setSingleChoiceItems(
                new String[]{"男", "女"}, selected,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String tempSex;
                        switch (which) {
                            case 1:
                                tempSex = "女";
                                break;
                            default:
                                tempSex = "男";
                                break;
                        }
                        Boolean temp = tempSex=="男"?true:false;
                        DataManager.user.getMyUser().put("isMan", temp);
                        final  String  t = tempSex;
                        DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    text[4] = t;
                                    tvSex.setText(text[4]);
                                    DataManager.user.setSex(true);
                                    Toast.makeText(Data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Data.this, "失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                }).show();
    }

    private void setPosition() {
        int p;
        switch (DataManager.user.getPosition()) {
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
                        String tempPosition;
                        switch (which) {
                            case 0:
                                tempPosition = "Cutter";
                                break;
                            case 1:
                                tempPosition = "Handler";
                                break;
                            case 2:
                                tempPosition = "I don't know";
                                break;
                            case 3:
                                tempPosition = "Cutter & Handler";
                                break;
                                default:
                                    tempPosition = "Cutter";
                                    break;
                        }
                        DataManager.user.getMyUser().put("position", tempPosition);
                        final String temp = tempPosition;
                        DataManager.user.getMyUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    text[5] = temp;
                                    tvPosition.setText(text[5]);
                                    DataManager.user.setPosition(text[5]);
                                    Toast.makeText(Data.this, "修改成功", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Data.this, "失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.dismiss();
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
        startActivityForResult(intent, PostmanHelper.REQUEST_BY_CROP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PostmanHelper.REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SetHeadFromGallery();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case PostmanHelper.REQUEST_CODE_CAMERA:
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
