package com.example.zfb_retrofit_post;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Query;
import util.UserTripleUtils;

/**
 * 模拟
 * 发送请求，服务器返回签名好的参数，再完成支付
 */

public class MainActivity extends AppCompatActivity {

    private String date = "jdf";
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
//                .baseUrl("http://10.0.2.2:8080/AndroidGet/servlet/")
                .baseUrl("http://p.3.cn/prices/mgets/")//京东商品价格
                .build();

        MyInterface myInterface = retrofit.create(MyInterface.class);
        Call<ResponseBody> responseBodyCall = myInterface.post("J_3133811", 1);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("print", "是否正确:" + response.isSuccessful() + "返回的值" + response.body().string());

                    Button button = (Button) findViewById(R.id.btn);
                    button.setText("异步？");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("print" , "错误");

            }
        });



    }


    interface MyInterface{
        @POST("http")
        retrofit2.Call<ResponseBody> post(@Query("skuIds") String id, @Query("type") Integer type);

//        String	 mobile = UserTripleUtils.encrypt("DAxDZVD2EkkcRXCc","15603051837");
//        String m = URLEncoder.encode(mobile, "utf-8");
////        String mm =  URLEncoder.encode( URLEncoder.encode(mobile, "utf-8"), "utf-8");
//
//        String  date = "201611";
        @POST("http")
        retrofit2.Call<ResponseBody> get(@Query("") String... linked);//传递一个数组参数
    }

    public void btnClick (View view) {
        getUserScor();
    }

    /**
     *
     */
    public void getUserScor(){

        String url = getInfo.getUrl();
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://112.96.28.145/17woservice/getUserScore/" + getInfo.getUrl())
                .build();

        MyInterface myInterface = retrofit.create(MyInterface.class);

        Call<ResponseBody> responseBodyCall = myInterface.get(getInfo.getString());

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("print", "" + response.isSuccessful());
                try {
                    Log.d("print", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("print", "出错啦！！！");

            }
        });
    }
}

class getInfo {

    public static String getUrl (){
        String	 mobile = UserTripleUtils.encrypt("DAxDZVD2EkkcRXCc","15603051837");
        try {
            mobile =  URLEncoder.encode( URLEncoder.encode(mobile, "utf-8"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String  date = "201611";

        return mobile + date + "/";
    }

    public static String[] getString(){
        String	 mobile = UserTripleUtils.encrypt("DAxDZVD2EkkcRXCc","15603051837");
        try {
            mobile =  URLEncoder.encode( URLEncoder.encode(mobile, "utf-8"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String  date = "201611";

        String[] s = {mobile, date};

        return s;
    }
}
