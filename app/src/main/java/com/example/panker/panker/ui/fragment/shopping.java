package com.example.panker.panker.ui.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.panker.panker.R;
import com.example.panker.panker.bean.User;
import com.example.panker.panker.ui.activity.Activity_main;
import com.example.panker.panker.ui.activity.Activity_me_data;
import com.example.panker.panker.ui.activity.Activity_me_power;
import com.example.panker.panker.ui.activity.Activity_me_setting;
import com.example.panker.panker.ui.activity.Activity_showhead;
import com.example.panker.panker.uilt.Tools.TittleManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ivory on 2016/7/19.
 * 天才兜儿又来啦！！！
 * hahahaha
 */
//public class shopping extends basefragment implements AdapterView.OnItemClickListener {
//    private View mContent;
//    //抽屉布局
//    private DrawerLayout mDrawerLayout;
//    //抽屉布局中的ListView
//    private ListView mDrawerList;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private CharSequence mDrawerTitle;
//    private String[]classify;
//    @Override
//    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mContent=inflater.inflate(R.layout.fragment_shopping,container,false);
//        classify = getResources().getStringArray(R.array.classify);
//        mDrawerTitle=classify[0];
//        return mContent;
//    }
//
//    @Override
//    protected void initEvent() {
//        initDrawerLayout();
//        initDrawerList();
//        //使用ActionBarDrawerToggle作为监听器
//        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout,
//                R.drawable.ic_drawer, R.string.drawer_open,
//                R.string.drawer_close) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//    }
//
//    private void initDrawerList() {
//        mDrawerList = (ListView) mContent.findViewById(R.id.left_drawer);
//        mDrawerList.setAdapter(new ArrayAdapter<String>(mActivity,
//                R.layout.drawer_list_item, classify));
//        mDrawerList.setOnItemClickListener(this);
//    }
//
//    private void initDrawerLayout() {
//        mDrawerLayout = (DrawerLayout) mContent.findViewById(R.id.drawer_layout);
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//                GravityCompat.START);
//    }
//    private void selectItem(int position) {
//        Fragment fragment = new GoodsFragment();
//        Bundle args = new Bundle();
//        args.putInt(GoodsFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_layout, fragment).commit();
//
//        // update selected item and title, then close the drawer
//        mDrawerList.setItemChecked(position, true);
////        setTitle(mPlanetTitles[position]);
//        mDrawerTitle=mDrawerList.getAdapter().getItem(position).toString();
//        Activity_main.tittleManager.setTitleStyle(TittleManager.TitleStyle.ONLY_BACK,mDrawerTitle.toString());
//        mDrawerLayout.closeDrawer(mDrawerList);
//    }
//
//
//    protected void onPostCreate(Bundle savedInstanceState) {
////        super.onPostCreate(savedInstanceState);
////        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//         selectItem(position);
//    }
//    public static class GoodsFragment extends Fragment{
//        public static final String ARG_PLANET_NUMBER = "planet_number";
//        public GoodsFragment(){
//        }
//        @Override
//        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
//            View rootView = inflater.inflate(R.layout.fragment_planet,
//                    container, false);
//            int i = getArguments().getInt(ARG_PLANET_NUMBER);
//            String goods = getResources()
//                    .getStringArray(R.array.classify)[i];
//
//            int imageId = getResources().getIdentifier(
//                    goods.toLowerCase(Locale.getDefault()), "drawable",
//                    getActivity().getPackageName());
//            ((ImageView) rootView.findViewById(R.id.image))
//                    .setImageResource(imageId);
//            getActivity().setTitle(goods);
//            return rootView;
//        }
//    }
//
//
//}
public class shopping extends basefragment{
    private View mContent;
    private WebView webView;
    private static final String url = "http://bong.m.tmall.com/?spm=a220m.6910245.0.0.1mIxQn&shop_id=125704545";
    //http://bong.m.tmall.com/?spm=a220m.6910245.0.0.1mIxQn&shop_id=125704545
    //https://shop.m.taobao.com/shop/shop_index.htm?spm=0.0.0.0&shop_id=36525342
    private static final String INJECTION_TOKEN = "**injection**";
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_shopping,container,false);
        webView = (WebView)mContent.findViewById(R.id.web);
        initWeb();
        return mContent;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
    }
    private void initWeb() {
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse response = super.shouldInterceptRequest(view, url);
            if(url != null && url.contains(INJECTION_TOKEN)) {
                String assetPath = url.substring(url.indexOf(INJECTION_TOKEN) + INJECTION_TOKEN.length(), url.length());
                try {
                    response = new WebResourceResponse(
                            "application/javascript",
                            "UTF8",
                            getContext().getAssets().open(assetPath)
                    );
                } catch (IOException e) {
                    e.printStackTrace(); // Failed to load asset file
                }
            }
            return response;
        }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
    }
}