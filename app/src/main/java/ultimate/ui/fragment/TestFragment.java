package ultimate.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.postman.ultimate.R;

import java.util.List;

import me.fangx.haorefresh.HaoRecyclerView;
import ultimate.bean.News;
import ultimate.uilt.interfaces.HomeListView;
import ultimate.uilt.rollviewpager.RollPagerView;

/**
 * Created by user on 2018/1/11.
 */

public class TestFragment extends BaseFragment implements HomeListView{
    private View mContent;//视图
    private SwipeRefreshLayout swipeRefreshLayout;
    private HaoRecyclerView haoRecyclerView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_test,container,false);
        swipeRefreshLayout = (SwipeRefreshLayout) mContent.findViewById(R.id.swiperefresh);
        haoRecyclerView = (HaoRecyclerView)mContent.findViewById(R.id.hao_recycleview);
        return null;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {

    }

    @Override
    public void showNetError(View.OnClickListener onClickListener) {

    }

    @Override
    public void refresh(List<News> data) {

    }

    @Override
    public void loadMore(List<News> data) {

    }
}
