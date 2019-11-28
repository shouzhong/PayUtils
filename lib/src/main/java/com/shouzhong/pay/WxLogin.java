package com.shouzhong.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxLogin {

    static String ACTION_RECEIVER = "action.receiver.WxResult";

    private Context mContext;
    private String appId;// 申请的
    private Callback callback;

    public WxLogin() {
    }

    public WxLogin with(Context context) {
        this.mContext = context;
        return this;
    }

    public WxLogin setAppId(String appId) {
        this.appId = appId;
        WXEntryActivity.APP_ID = appId;
        return this;
    }

    /**
     * 回调
     *
     * @param callback
     * @return
     */
    public WxLogin setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public void start() {
        final IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
        if (!api.isWXAppInstalled()) {
            callback.failure("-100", "未安装微信");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        boolean isSend = api.sendReq(req);
        if (!isSend) {
            callback.failure("-101", "登录失败");
            return;
        }
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
                    callback.success(intent.getStringExtra("data"));
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
