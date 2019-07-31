package com.shouzhong.pay.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shouzhong.pay.Callback;
import com.shouzhong.pay.PayUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickWx(View v) {
        // 测试，参数不能用，请填入正确的参数
        PayUtils.wx().with(this)
                .setAppId("wx5465ef5346755e9a")
                .setNonceStr("nDlngiqitdtqAyCFlcczBmFiCxlonmhB")
                .setPartnerId("1235542732")
                .setPrepayId("wx2910411342936549283e30921199593822")
                .setTimeStamp("1564368078")
                .setSign("9A11839640315D70658405336D196624")
                .setCallback(new Callback() {
                    @Override
                    public void success() {
                        Log.e("MainActivity", "wx->success");
                    }

                    @Override
                    public void failure(String errorCode, String errorMessage) {
                        Log.e("MainActivity", "wx->failure->" + errorCode + ":" + errorMessage);
                    }

                    @Override
                    public void cancel() {
                        Log.e("MainActivity", "wx->cancel");
                    }
                })
                .start();
    }

    public void onClickAli(View v) {
        // 测试，参数不能用，请填入正确的参数
        PayUtils.ali().with(this)
                .setPaySign("123456789")
                .setShowLoading(false)
                .setCallback(new Callback() {
                    @Override
                    public void success() {
                        Log.e("MainActivity", "ali->success");
                    }

                    @Override
                    public void failure(String errorCode, String errorMessage) {
                        Log.e("MainActivity", "ali->failure->" + errorCode + ":" + errorMessage);
                    }

                    @Override
                    public void cancel() {
                        Log.e("MainActivity", "ali->cancel");
                    }
                })
                .start();
    }
}
