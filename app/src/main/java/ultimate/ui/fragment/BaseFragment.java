package ultimate.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ultimate.ui.activity.Main;

/**
 * Created by user on 2016/8/20.
 */
public abstract class BaseFragment extends Fragment {
    //protected final String TAG = this.getClass().getSimpleName();
    protected Main mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (Main) getActivity();
        initData();
         View mContentView = initView(inflater, container, savedInstanceState);
        initEvent();
        return mContentView;
    }
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initEvent();

    /**
     * 初始化数据加载
     */
    protected abstract void initData() ;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

}
