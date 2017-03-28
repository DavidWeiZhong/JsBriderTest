package com.tsy.paydemo;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.tsy.sdk.pay.PayUtils;
import com.tsy.sdk.pay.alipay.Alipay;
import com.tsy.sdk.pay.weixin.WXPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editWXParam, editAlipayParam;
    private Button btnWXPay, btnAlipay, btnWXClear, btnWXPaste, btnAliPayClear, btnAliPayPaste, btnGetIp;
    // 商户PID
    public static final String PARTNER = "2088522534545290";
    // 商户收款账号
    public static final String SELLER = "bqssvg8204@sandbox.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKxeLSEU1SQqJcyk6V9NO336tPoTmW/aGrB8pzcNI5cVTddS464QZQ3eYFgFD87jbv58ErYE9h7MAgm2m2RwVdoHL58fhhi77jzxe1h3ZU4qg4tLq1Hix75Ta6OlX7f15Ub+b0VQobQ54xOvKRlczV/osJ3/PbutPh9Srs7V4DFTAgMBAAECgYBzWar14o0o4JPfOdV7k6HmkuqeGJOsSNdnmGQG8WIJ75XiLXR0vkACYfkzrl9/4rAa2kljSjqzLW+HtI6VTqAEaOfmeImKofMVqVsd/tLbuNMu4YIBIc76xAfi4UdfbCN23GRyZczgbRpgx7NIOcRHuG6QDbD/T9uo5TqfODqoIQJBAN+5RoFN92CNw2vD3nZ/lDhR7HX9pgenGjQejRzEg1z9cZggjdRgq4eri6gWZ4qZOyM4HsIbpud+5DYjZNzrZOkCQQDFPDCS00h6yBt/PqdwcBV47ihrvjWuRnMwwv2RVkQjZrZ/0b3VqHbyJqPDwpl2g0jx73nbB7EkJjcCO+86By7bAkEAnjU+J6iD/HKjtGyRwAieP2mnpxTVOWow/JRyQ9qv7q2HjAXRZlNqWYm1PAuD8x5DFLfWAHxffitcpPBInnWUKQJANEaBsUiimzhSYWVfx6NNbaHhwLzHSYuOmEv2HQE6Sg+9Kx+SKwDdlcy79vSq7Ahb2xPXvj/JqYUfa04rV7vzpQJAD+bqlfdE87+eqy2YKLo1Uc6qf95kDHMps69ECpxx6xt3sKNcFg1bieGFhiPIXLysrLAFhYB/FyHrbRmW+woNKQ==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editAlipayParam = (EditText)findViewById(R.id.editAlipayParam);
        editWXParam = (EditText)findViewById(R.id.editWXParam);
        btnAlipay = (Button)findViewById(R.id.btnAliPay);
        btnWXPay = (Button)findViewById(R.id.btnWXPay);
        btnWXClear = (Button)findViewById(R.id.btnWXClear);
        btnWXPaste = (Button)findViewById(R.id.btnWXPaste);
        btnAliPayClear = (Button)findViewById(R.id.btnAliPayClear);
        btnAliPayPaste = (Button)findViewById(R.id.btnAliPayPaste);
        btnGetIp = (Button)findViewById(R.id.btnGetIp);

        btnAlipay.setOnClickListener(this);
        btnWXPay.setOnClickListener(this);
        btnWXClear.setOnClickListener(this);
        btnWXPaste.setOnClickListener(this);
        btnAliPayClear.setOnClickListener(this);
        btnAliPayPaste.setOnClickListener(this);
        btnGetIp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        switch (v.getId()) {
            case R.id.btnWXPay:
                String wx_pay_param = editWXParam.getText().toString();
                if(TextUtils.isEmpty(wx_pay_param)) {
                    Toast.makeText(getApplication(), "请输入参数", Toast.LENGTH_SHORT).show();
                    return;
                }
                doWXPay(wx_pay_param);
                break;

            case R.id.btnWXClear:
                editWXParam.setText("");
                break;

            case R.id.btnWXPaste:
                ClipboardManager cbm=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                editWXParam.setText(cbm.getText());
                break;

            case R.id.btnAliPay:
//                String alipay_pay_param = editAlipayParam.getText().toString();
                String alipay_pay_param = payInfo;
                if(TextUtils.isEmpty(alipay_pay_param)) {
                    Toast.makeText(getApplication(), "请输入参数", Toast.LENGTH_SHORT).show();
                    return;
                }
                doAlipay(alipay_pay_param);
                break;

            case R.id.btnAliPayClear:
                editAlipayParam.setText("");
                break;

            case R.id.btnAliPayPaste:
                ClipboardManager cbm2=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                editAlipayParam.setText(cbm2.getText());
                break;

            case R.id.btnGetIp:
                String ip = PayUtils.getIpAddress();
                if(ip != null) {
                    Toast.makeText(getApplication(), ip, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "获取ip失败", Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }
    }

    /**
     * 支付宝支付
     * @param pay_param 支付服务生成的支付参数
     */
    private void doAlipay(String pay_param) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//沙箱环境
        new Alipay(this, pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDealing() {
                Toast.makeText(getApplication(), "支付处理中...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        Toast.makeText(getApplication(), "支付失败:支付结果解析错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_NETWORK:
                        Toast.makeText(getApplication(), "支付失败:网络连接错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付错误:支付码支付失败", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getApplication(), "支付错误", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        }).doPay();
    }

    /**
     * 微信支付
     * @param pay_param 支付服务生成的支付参数
     */
    private void doWXPay(String pay_param) {
        String wx_appid = "wxXXXXXXX";     //替换为自己的appid
        WXPay.init(getApplicationContext(), wx_appid);      //要在支付前调用
        WXPay.getInstance().doPay(pay_param, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        Toast.makeText(getApplication(), "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        Toast.makeText(getApplication(), "参数错误", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
}
