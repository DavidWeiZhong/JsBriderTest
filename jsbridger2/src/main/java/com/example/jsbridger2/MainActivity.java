package com.example.jsbridger2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private final Activity mActivity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);//进度条
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mWebView.addJavascriptInterface(this, "android");//让js支持android,android就可以随便写了，就是要在js中window.name.android中的方法，中的name要一一对应
        WebSettings settings = mWebView.getSettings();

        settings.setUseWideViewPort(true);//设置自适应屏幕大小
        settings.setLoadWithOverviewMode(true);

        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);//支持双击缩放，不支持触摸缩放
//        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//是滚动条不占位,这个方法，结合setuswideviewport,和setLoad/withOverviewMode方法使用的话，则自动适应屏幕失效
        //WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d("print", "你好我是android");//会自动调用,js调用android
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                MainActivity.this.setTitle("loading...");

            if (mProgressBar.getVisibility() == View.GONE) {
                Log.d("print", "----》》》》");
            } else {
                Log.d("print", "++++++++++");
            }
//                MainActivity.this.setProgress(newProgress * 100);
                if (newProgress == 100) {
                    MainActivity.this.setTitle("go。。。");
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
                mProgressBar.setProgress(newProgress);
                mActivity.setProgress(newProgress);

                super.onProgressChanged(view, newProgress);

            }
        });
        //WebViewClient就是帮助WebView处理各种通知、请求事件的
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                return true;
            }

            //            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
////                return super.shouldOverrideUrlLoading(view, request);
//            重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//            view.loadUrl(url);
//                view.loadUrl("");
//                return true;
//            }
        });

//        webView.loadUrl("file:///android_asset/demo.html");
//        mWebView.loadUrl("file:///android_asset/demo.html");
        mWebView.loadUrl("file:///android_asset/index.html");

    }

    /**
     * js调用android
     */
    @JavascriptInterface
    public void toast(){
        Toast.makeText(this, "js调用android", Toast.LENGTH_LONG).show();
    }
}
