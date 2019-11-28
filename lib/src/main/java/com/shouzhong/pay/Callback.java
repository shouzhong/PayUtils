package com.shouzhong.pay;

public interface Callback {

    void success(String data);

    void failure(String errorCode, String errorMessage);

    void cancel();

}
