package com.example.jsh5url_otherapp;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.zxinsight.share.activity.MWWXHandlerActivity;

/**
 * Created by ${邱伟中} on 2016/12/23 0023.上午 9:55
 */
public class WXEntruAcitviyt extends MWWXHandlerActivity{
    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        // super.onReq(req);必须加
        super.onReq(req);
        //todo: 在下面添加你可能需要实现的代码，如果没有则留空
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp response) {
        // super.onResp(response);必须加
        super.onResp(response);
        //todo: 在下面添加你可能需要实现的代码，如果没有则留空
    }
}
