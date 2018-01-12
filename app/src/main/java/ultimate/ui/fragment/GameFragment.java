package ultimate.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.postman.ultimate.R;
import ultimate.bean.Game;
import ultimate.ui.activity.Web;
import ultimate.ui.adapter.GameAdapter;
import ultimate.uilt.mylistview.MyListView;
import ultimate.uilt.tools.DataManager;


/**
 * Created by Ivory on 2016/7/19.
 * hahahaha
 */
public class GameFragment extends BaseFragment implements View.OnClickListener {
    private View mContent;
    private MyListView mListView;
    private GameAdapter mAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_game,container,false);
        mListView = (MyListView)mContent.findViewById(R.id.gameList);
        mAdapter = new GameAdapter(mActivity,R.layout.game_listview,DataManager.getGame());
        mListView.setAdapter(mAdapter);
        mListView.setFocusable(false);
        return mContent;
    }

    @Override
    protected void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Game t = DataManager.getGame().get(i);
                //Toast.makeText(mActivity, t.getNews_url(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mActivity,Web.class);
                intent.putExtra("url", t.getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
    }
}