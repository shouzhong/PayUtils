# PayUtils
## 说明
支付工具类，集成了微信支付和支付宝支付。
## 使用
### 依赖
```
implementation 'com.shouzhong:PayUtils:1.0.2'
```
### 代码
微信支付，以下参数最好后端生成，这里不提供前端生成的方法，在前端生成不安全
```
PayUtils.wx().with(Context)
        .setAppId()// appId
        .setNonceStr()// 随机字符串
        .setPartnerId()// 商户id
        .setPrepayId()// 预支付id
        .setTimeStamp()// 时间戳
        .setSign()// 签名
        .setCallback(new Callback() {
            @Override
            public void success(String data) {
                // 支付成功，data为空
            }

            @Override
            public void failure(String errorCode, String errorMessage) {
                //  支付失败
            }

            @Override
            public void cancel() {
                // 用户取消
            }
        })
        .start();
```
支付宝支付，以下参数最好后端生成，这里不提供前端生成的方法，在前端生成不安全
```
PayUtils.ali().with(Activity)
        .setPaySign()// 支付信息
        .setShowLoading()// 是否展示自带ProgressDialog
        .setCallback(new Callback() {
            @Override
            public void success(String data) {
                // 支付成功，data为空
            }

            @Override
            public void failure(String errorCode, String errorMessage) {
                //  支付失败
            }

            @Override
            public void cancel() {
                // 用户取消
            }
        })
        .start();
```
微信登录
```
LoginUtils.wx().with(context)
        .setAppId()// appId
        .setCallback(new Callback() {
                    @Override
                    public void success(String data) {
                        // 登录成功，data为code，把code给后台去获取用户信息等
                    }

                    @Override
                    public void failure(String errorCode, String errorMessage) {
                        //  登录失败
                    }

                    @Override
                    public void cancel() {
                        // 用户取消
                    }
                })
                .start();
```
## 混淆
```
# 支付宝支付
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}
-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}
-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}
# 微信支付
-keep class com.tencent.mm..opensdk.** {*;}
-dontwarn com.tencent.mm.opensdk.**
```