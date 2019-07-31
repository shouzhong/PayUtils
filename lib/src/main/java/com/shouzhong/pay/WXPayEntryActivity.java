package com.shouzhong.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    static String APP_ID;

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
        APP_ID = null;
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
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent = new Intent();
            intent.setAction(WxPay.ACTION_RECEIVER);
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:// 成功
                    intent.putExtra("code", 0);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 用户取消
                    intent.putExtra("code", -2);
                    break;
                default:// 其他错误
                    intent.putExtra("code", -1);
                    intent.putExtra("error_code", String.valueOf(resp.errCode));
                    intent.putExtra("error_message", resp.errStr);
                    break;
            }
            sendBroadcast(intent);
            finish();
        }
    }
}
