package com.tamic.simplebrowse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import com.tamic.jswebview.browse.CallBackFunction;
import com.tamic.jswebview.browse.JsWeb.CustomWebChromeClient;
import com.tamic.jswebview.browse.JsWeb.CustomWebViewClient;
import com.tamic.jswebview.browse.JsWeb.JavaCallHandler;
import com.tamic.jswebview.browse.JsWeb.JsHandler;
import com.tamic.jswebview.view.ProgressBarWebView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // UI references.
    private ProgressBarWebView mProgressBarWebView;
    private ArrayList<String> mHandlers = new ArrayList<>();

    ValueCallback<Uri> mUploadMessage;
    private static CallBackFunction mfunction;

    int RESULT_CODE = 0;
    private String mJavascrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
    }


    @JavascriptInterface
    public void call() {
        Toast.makeText(this, "call", Toast.LENGTH_LONG).show();
    }

    private void initWebView() {

        mProgressBarWebView = (ProgressBarWebView) findViewById(R.id.login_progress_webview);

        mProgressBarWebView.setWebViewClient(new CustomWebViewClient(mProgressBarWebView.getWebView()) {
            @Override
            public void onPageFinished(WebView view, String url) {
                CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(MainActivity.this);
                cookieSyncManager.sync();
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
//                cookieManager.setCookie(url, "username=邱伟中");
                CookieSyncManager.getInstance().sync();
                String cookie = cookieManager.getCookie(url);
                Toast.makeText(MainActivity.this, "----" + cookie, Toast.LENGTH_LONG).show();
                super.onPageFinished(view, url);
            }

            @Override
            public String onPageError(String url) {
                //指定网络加载失败时的错误页面
                return "file:///android_asset/error.html";
            }

            @Override
            public Map<String, String> onPageHeaders(String url) {

                // 可以加入header加入header 一般直接使用webView.load(url, header)

                return null;
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;//有待解决
                pickFile();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }


        });

        mProgressBarWebView.setWebChromeClient(new CustomWebChromeClient(mProgressBarWebView.getProgressBar()) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                view.requestFocus();//使webview的输入框获取焦点的时候不会制动放大
            }
        });


        // 打开页面，也可以支持网络url
        try {
            InputStream input = getAssets().open("demo.html");
            int size;
            size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            // byte buffer into a string
            mJavascrips = new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // String html = readFile(is);

        mProgressBarWebView.loadUrl("http://10.0.2.2:8080/jsbrider/index.jsp");//要在原生模拟器上才有用，cookie
//        mProgressBarWebView.loadUrl("file:///android_asset/demo.html");//原生模拟器也没用
//        mProgressBarWebView.loadDataWithBaseURL("file:///android_asset/demo.html",
//                mJavascrips, "text/html", "UTF-8", null);//原生模拟器也没有用

        mHandlers.add("login");
        mHandlers.add("callNative");
        mHandlers.add("callJs");
        mHandlers.add("open");
        mHandlers.add("call");


        //回调js的方法
        mProgressBarWebView.registerHandlers(mHandlers, new JsHandler() {

            @Override
            public void OnHandler(String handlerName, String responseData, CallBackFunction function) {

                if (handlerName.equals("login")) {

                    Toast.makeText(MainActivity.this, responseData, Toast.LENGTH_SHORT).show();

                } else if (handlerName.equals("callNative")) {

                    Toast.makeText(MainActivity.this, responseData, Toast.LENGTH_SHORT).show();

                    function.onCallBack("我在上海");

                } else if (handlerName.equals("callJs")) {

                    Toast.makeText(MainActivity.this, responseData, Toast.LENGTH_SHORT).show();

                    // 想调用你的方法：

                    function.onCallBack("好的 这是图片地址 ：xxxxxxx");


                } else if (handlerName.equals("open")) {

                    mfunction = function;

                    pickFile();

                } else if (handlerName.equals("call")) {
//                    Toast.makeText(MainActivity.this, "call" + responseData, Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("tel:" + responseData);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    Toast.makeText(MainActivity.this, "call" + responseData, Toast.LENGTH_LONG).show();
                    try {
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "call" + responseData, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "call,,,.." + responseData, Toast.LENGTH_LONG).show();
                    }
//                    Intent intent2 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:10086"));
//                    startActivity(intent2);

                }

            }
        });

        // 调用js,即一开始就执行的js代码

        mProgressBarWebView.callHandler("callNative", "hello H5, 我是java", new JavaCallHandler() {
            @Override
            public void OnHandler(String handlerName, String jsResponseData) {
                Toast.makeText(MainActivity.this, "h5返回的数据：" + jsResponseData, Toast.LENGTH_SHORT).show();
            }
        });
        //发送消息给js，即一开始就执行的android代码
        mProgressBarWebView.send("哈喽", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                //初始化init连接的时候。
                Toast.makeText(MainActivity.this, data + ">>>>>>", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage) {
                Toast.makeText(this, "mUploadMessage为空", Toast.LENGTH_LONG).show();
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

            mfunction.onCallBack(intent.getData().toString());
            Toast.makeText(this, "" + intent.getData().toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressBarWebView.getWebView() != null) {
            mProgressBarWebView.getWebView().destroy();
        }
    }
}
