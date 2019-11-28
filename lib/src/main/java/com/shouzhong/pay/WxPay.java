package com.shouzhong.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxPay {

    static String ACTION_RECEIVER = "action.receiver.WxPayResult";

    private Context mContext;
    private PayReq request;
    private Callback callback;

    WxPay() {
        request = new PayReq();
        request.packageValue = "Sign=WXPay";
    }

    public WxPay with(Context context) {
        this.mContext = context;
        return this;
    }

    /**
     * appId
     *
     * @param appId
     * @return
     */
    public WxPay setAppId(String appId) {
        request.appId = appId;
        WXPayEntryActivity.APP_ID = appId;
        return this;
    }

    /**
     * 商户id
     *
     * @param partnerId
     * @return
     */
    public WxPay setPartnerId(String partnerId) {
        request.partnerId = partnerId;
        return this;
    }

    /**
     * 随机字符串
     *
     * @param nonceStr
     * @return
     */
    public WxPay setNonceStr(String nonceStr) {
        request.nonceStr = nonceStr;
        return this;
    }

    /**
     * 时间戳
     *
     * @param timeStamp
     * @return
     */
    public WxPay setTimeStamp(String timeStamp) {
        request.timeStamp = timeStamp;
        return this;
    }

    /**
     * 预支付id
     *
     * @param prepayId
     * @return
     */
    public WxPay setPrepayId(String prepayId) {
        request.prepayId = prepayId;
        return this;
    }

    /**
     * 签名
     *
     * @param sign
     * @return
     */
    public WxPay setSign(String sign) {
        request.sign = sign;
        return this;
    }

    /**
     * 回调
     *
     * @param callback
     * @return
     */
    public WxPay setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 开始支付
     *
     */
    public void start() {
        final IWXAPI api = WXAPIFactory.createWXAPI(mContext, request.appId);
        if (!api.isWXAppInstalled()) {
            callback.failure("-100", "未安装微信");
            return;
        }
        boolean isSend = api.sendReq(request);
        if (!isSend) {
            callback.failure("-101", "调起微信支付失败");
            return;
        }
        request = null;
        mContext.registerReceiver(new Receiver(), new IntentFilter(ACTION_RECEIVER));
    }

    class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mContext.unregisterReceiver(this);
            mContext = null;
            int code = intent.getIntExtra("code", -2);
            switch (code) {
                case 0:
                    callback.success(null);
                    break;
                case -1:
                    callback.failure(intent.getStringExtra("error_code"), intent.getStringExtra("error_message"));
                    break;
                case -2:
                    callback.cancel();
                    break;
            }
            callback = null;
        }
    }

}
