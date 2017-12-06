package ultimate.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ultimate.ui.activity.ShowHead;
import ultimate.bean.User;
import ultimate.ui.activity.Data;

import com.example.postman.ultimate.R;

import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.PostmanHelper;

/**
 * Created by Ivory on 2016/7/19.
 * hahahaha
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private View mContent;
    private String nickname, team, myself;
    public TextView tvNickname, tvTeam, tvMyself, tvEdit;
    public RelativeLayout rlMeData;
    public ImageView imgHead;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_me, container, false);
        imgHead = (ImageView) mContent.findViewById(R.id.me_head);
        tvNickname = (TextView) mContent.findViewById(R.id.me_nickname);
        tvTeam = (TextView) mContent.findViewById(R.id.me_team);
        tvMyself = (TextView) mContent.findViewById(R.id.me_myself);
        tvEdit = (TextView) mContent.findViewById(R.id.me_edit);
        tvNickname.setText(nickname);
        tvTeam.setText(team);
        tvMyself.setText(myself);

//        me_power = (LinearLayout) mContent.findViewById(R.id.me_power);
//        me_setting = (LinearLayout) mContent.findViewById(R.id.me_setting);
        rlMeData = (RelativeLayout) mContent.findViewById(R.id.rl);
//        Toast.makeText(mActivity,user.getBackground().toString(),Toast.LENGTH_LONG).show();
        rlMeData.setBackground(PostmanHelper.bitmap2drawable(DataManager.user.getBackground()));
        imgHead.setImageBitmap(PostmanHelper.toRoundCornerImage(DataManager.user.getHead(), 180));
        return mContent;
    }

    @Override
    protected void initData() {
        // head=new Head(0);
        super.initData();
        nickname = DataManager.user.getNickname();
        team = DataManager.user.getTeam();
        myself = DataManager.user.getMyself();
    }

    @Override
    protected void initEvent() {
//        me_power.setOnClickListener(this);
        rlMeData.setOnClickListener(this);
//        me_setting.setOnClickListener(this);
        imgHead.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.me_edit:
                intent = new Intent(mActivity, Data.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.me_head:
                intent = new Intent(mActivity, ShowHead.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.rl:
                setBackground();
                break;
        }
    }

    private void setBackground() {
        final String[] msg = getString(R.string.data_public_msg).split(";");
        CharSequence[] items = {msg[0], msg[1]};
        new AlertDialog.Builder(mActivity)
                .setTitle(getString(R.string.image_from))
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PostmanHelper.REQUEST_BY_GALLERY);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PostmanHelper.REQUEST_CODE_CAMERA);
                        }
                    }
                })
                .create().show();
    }
}