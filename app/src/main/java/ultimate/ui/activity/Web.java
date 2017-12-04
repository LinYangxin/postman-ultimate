package ultimate.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.panker.ultimate.R;
import ultimate.uilt.tools.TittleManager;

/**
 * Created by user on 2017/1/17.
 */

public class Web extends Activity {
    private WebView webView;
    private TittleManager tittleManager;
    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initData();
        initWeb();
        initView();
        initEvent();
    }
    private void initData(){
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
    }
    private void initEvent(){
        tittleManager.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void initView(){
        tittleManager=new TittleManager(this);
        tittleManager.setTitleStyle(TittleManager.TitleStyle.BACK_AND_FAVORITE,"Panker");
    }
    private void initWeb() {
        webView = (WebView) findViewById(R.id.ww);
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

    }
}
