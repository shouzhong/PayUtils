package com.shouzhong.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    static String APP_ID;
    // baseResp.getType() 登录
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    // 分享
    private static final int RETURN_MSG_TYPE_SHARE = 2;


    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == RETURN_MSG_TYPE_LOGIN) {
            Intent intent = new Intent();
            intent.setAction(WxLogin.ACTION_RECEIVER);
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String data = ((SendAuth.Resp) resp).code;
                    intent.putExtra("code", 0);
                    intent.putExtra("data", data);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    intent.putExtra("code", -2);
                    break;
                default:
                    intent.putExtra("code", -1);
                    intent.putExtra("error_code", String.valueOf(resp.errCode));
                    intent.putExtra("error_message", resp.errStr);
                    break;
            }
            sendBroadcast(intent);
        }
        finish();
    }
}
