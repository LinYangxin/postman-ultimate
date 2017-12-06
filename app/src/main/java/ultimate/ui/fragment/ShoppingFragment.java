package ultimate.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.postman.ultimate.R;

import java.io.IOException;

/**
 * Created by Ivory on 2016/7/19.
 * 天才兜儿又来啦！！！
 * hahahaha
 */
public class ShoppingFragment extends BaseFragment {
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