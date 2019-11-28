package com.shouzhong.pay;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class AliPay {

    private Activity activity;
    private String paySign;
    private boolean isShowLoading;
    private Callback callback;

    AliPay() {
    }

    public AliPay with(Activity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * 签名
     *
     * @param paySign
     * @return
     */
    public AliPay setPaySign(String paySign) {
        this.paySign = paySign;
        return this;
    }

    /**
     * 是否展示自带ProgressDialog
     *
     * @param b
     * @return
     */
    public AliPay setShowLoading(boolean b) {
        this.isShowLoading = b;
        return this;
    }

    /**
     * 回调
     *
     * @param callback
     * @return
     */
    public AliPay setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 开始支付，注意，为防止内存泄露，请在onDestroy中调用AsyncTask.cancel(boolean mayInterruptIfRunning)
     *
     * @return
     */
    public AsyncTask<PayTask, Integer, Map<String, String>> start() {
        @SuppressWarnings("all")
        final AsyncTask<PayTask, Integer, Map<String, String>> task = new AsyncTask<PayTask, Integer, Map<String, String>>() {
            @Override
            protected Map<String, String> doInBackground(PayTask... payTasks) {
                activity = null;
                Map<String, String> result = payTasks[0].payV2(paySign, isShowLoading);
                paySign = null;
                isShowLoading = false;
                return result;
            }

            @Override
            protected void onPostExecute(Map<String, String> map) {
                String code = map.get("resultStatus");
                if (TextUtils.equals(code, "9000")) {
                    callback.success(null);
                } else if (TextUtils.equals(code, "6001")) {
                    callback.cancel();
                } else {
                    callback.failure(code, map.get("result"));
                }
                callback = null;
            }
        };
        task.execute(new PayTask(activity));
        return task;
    }
}
