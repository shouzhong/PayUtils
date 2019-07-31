package com.shouzhong.pay;

public class PayUtils {

    public static WxPay wx() {
        return new WxPay();
    }

    public static AliPay ali() {
        return new AliPay();
    }

}
