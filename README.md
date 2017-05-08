# JsBriderTest
js与webview的交互

#cookie_app_web
通过本地native设置网页cookie，和在本地获得网页的cookie


#2各种设置

/设置是否支持缩放
webSettings.setSupportZoom(true);
webSettings.setBuiltInZoomControls(true);

//设置是否显示缩放按钮
webSettings.setDisplayZoomControls(true);

//设置WebView是否允许执行JavaScript脚本，默认false，不允许
webSettings.setJavaScriptEnabled(true);

//设置自适应屏幕宽度
webSettings.setUseWideViewPort(true);
webSettings.setLoadWithOverviewMode(true);

//mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//是滚动条不占位,这个方法，结合setuswideviewport,和setLoad/withOverviewMode方法使用的话，则自动适应屏幕失效


//        mWebView.loadUrl("http://10.0.2.2:8080/jsbrider/index.jsp");
//        mWebView.loadUrl("file:///android_asset/demo.html");//需要在assets目录中新建一个demo.html


#android 与web方法之间的调用
首先webSettings.setJavaScriptEnabled(true);

1， 无参数调用 JS的方法
//Button按钮 无参调用HTML js方法
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                // 无参数调用 JS的方法
                mWebView.loadUrl("javascript:javacalljs()");

            }
        });


2，传递参数调用JS的方法
findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                // 传递参数调用JS的方法
                mWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");
            }
        });


3，js调用Android native方法和传递参数
 mWebView.addJavascriptInterface(MainActivity.this,"android");//让js支持android,android就可以随便写了，就是要在js中window.name.android中的方法，中的name要一一对应
 android字符的意义就在于onclick="window.android.startFunction()"
 <input type="button"  value="点击调用java代码" onclick="window.android.startFunction()" /><br/>
<input type="button"  value="点击调用java代码并传递参数" onclick="window.android.startFunction('http://blog.csdn.net/Leejizhou')"  />

//由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void startFunction() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_LONG).show();

            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();

            }
        });

    }
    
    
    #setWebViewClient()方法，辅助WebView处理各种通知、请求事件：
    
    webView.setWebViewClient(new WebViewClient(){
    @Override
    //只要重写此方法，就能在本应用中加载网页
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;//返回值时true的时候控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器
    }
   @Override  
   public void onReceivedError(WebView view, int errorCode,  
      String description, String failingUrl) {  
//收到错误信息的时候，系统执行此操作.
//比如当出现404错误码时，我们可以自己写个html放在asset文件夹中，把webView隐藏掉而显示本地的网页。
        }  

        @Override  
        public void onPageStarted(WebView view, String url, Bitmap favicon) {  
            super.onPagStarted(view, url, favicon);  
            //若想在网页开始加载时执行一些操作，重写该方法 
        }  

        @Override  
        public void onPageFinished(WebView view, String url) {  
            super.onPageFinished(view, url);  
            //若想在网页结束时执行一些操作，重写该方法 
        } 
}
);
 
 
 #setWebChromeClient()方法，辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等：
 
 webView.setWebChromeClient(new WebChromeClient(){
 
 
            @Override//当网页触发alert（）的时候会触发Android的onJsAlert方法
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d("print", "你好我是android");//会自动调用,js调用android
                return super.onJsAlert(view, url, message, result);
            }
            
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        progressBar.setProgress(newProgress);
//newProgress的值为1到100的整数，我们可以利用这个参数使用ProgressBar或ProgressDialog显示加载进度
        } 
   }
    @Override
    public void onReceivedTitle(WebView view, String title) { 
       textView.setText(title);//参数title为网页的标题，可以用一个textView来显示。
   }
}

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
              
                choosePicture();//当网页的input为fill时即选择文件的时候，会自动触发这个方法，在这里可以进行图片的选择。
//                Toast.makeText(PregnancyActivity.this,  "onShowFileChooser" + mUris, Toast.LENGTH_SHORT).show();
                return true;
            }
);
