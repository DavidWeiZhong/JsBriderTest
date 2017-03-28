package com.example.otherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zxinsight.MLink;
import com.zxinsight.MWConfiguration;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLink.getInstance(this).registerWithAnnotation(this);
        setContentView(R.layout.activity_main);
        MLink.getInstance(this).deferredRouter();
        MWConfiguration config = new MWConfiguration(this);
        config.setDebugModel(true);
        Button button = new Button(this);
        button.setText("跳转到OtherActivity");
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });

        Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = i_getvalue.getData();
            if(uri != null){
                String name = uri.getQueryParameter("name");
                String age= uri.getQueryParameter("age");
                Toast.makeText(this, name + "---" + age + "---" + action, Toast.LENGTH_LONG).show();
            }
        }

        mowen();
    }

    public void mowen() {
        // TODO其他代码
        //跳转router调用

        if (getIntent().getData()!=null) {
            MLink.getInstance(this).router(this, getIntent().getData());
            //跳转后结束当前activity
            finish();
        } else {
            //如果需要应用宝跳转，则调用。否则不需要
            //MLink.getInstance(this).checkYYB();
            //跳转到首页
            gotoHome();
        }
    }
    public void gotoHome() {
        startActivity(new Intent(MainActivity.this, OtherActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
