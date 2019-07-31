package com.shouzhong.pay;

public interface Callback {

    void success();

    void failure(String errorCode, String errorMessage);

    void cancel();

}
