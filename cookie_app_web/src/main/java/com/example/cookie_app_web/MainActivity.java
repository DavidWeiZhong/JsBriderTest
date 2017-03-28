package com.example.cookie_app_web;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button mButton,mButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webView);
        mButton = (Button) findViewById(R.id.btn);
        mButton2 = (Button) findViewById(R.id.btn2);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//本地打开
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }
        });

        mWebView.loadUrl("http://www.baidu.com");//反正就是服务器上面总本地设置给js就有用，从本地就没用
//        syncCookie("http://www.baid.com", "name=qiuweizhong");
//        mWebView.loadUrl("http://10.0.2.2:8080/jsbrider/MyJsp.jsp");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncCookie("http://10.0.2.2:8080/jsbrider/MyJsp.jsp", "username=邱伟中");//成功了，就差cookie_web_app了
                Toast.makeText(MainActivity.this, "设置成功", Toast.LENGTH_LONG).show();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCookie("http://10.0.2.2:8080/jsbrider/MyJsp.jsp");//成功，还是只能在服务器上面获得cookie，本地不能
            }
        });

//        mWebView.loadUrl("file:///android_asset/demo.html");//本地现无法同步
//        syncCookie("file:///android_asset/demo.html", "name=qiuweizhong");
    }

    @JavascriptInterface
    public void startFunction() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键时的操作
                mWebView.goBack();   //后退
                //webview.goForward();//前进
                return true;    //已处理
            }
            this.finish();
            return true;
        }
        return false;
    }
    /**
     * 将cookie同步到WebView
     * @param url WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public boolean syncCookie(String url,String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(getApplicationContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
//        // 接受服务器cookie
//        cookieManager.setAcceptCookie(true);
//        //移除之前的cookie
//        cookieManager.removeSessionCookie();
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);//在web端的js中也能获得这个cookie
        Toast.makeText(this, "---->>>" + newCookie, Toast.LENGTH_LONG).show();
        return TextUtils.isEmpty(newCookie)?false:true;
    }

    public void getCookie(String url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(getApplicationContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        String newCookie = cookieManager.getCookie(url);//在web端的js中也能获得这个cookie
        Toast.makeText(this, "从js上获得的cookie为：" + newCookie, Toast.LENGTH_LONG).show();
    }
}
