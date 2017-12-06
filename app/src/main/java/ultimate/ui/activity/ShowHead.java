package ultimate.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.postman.ultimate.R;

import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.TitleManager;
import ultimate.bean.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by user on 2016/9/27.
 */
public class ShowHead extends Activity {
    private TitleManager titleManager;
    private ImageView head;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showhead);
        initView();
    }
    private void initView(){
        titleManager =new TitleManager(this);
        titleManager.setTitleStyle(TitleManager.TitleStyle.BACK_AND_SAVE,getString(R.string.showhead_title));
        titleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHead();
                Toast.makeText(ShowHead.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
            }
        });
        head=(ImageView)findViewById(R.id.head);
        head.setImageBitmap(DataManager.user.getHead());
    }
    private void saveHead(){
        File appDir = new File(getExternalStorageDirectory(), getString(R.string.app_name));
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataManager.user.getHead().compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
       sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }
}
