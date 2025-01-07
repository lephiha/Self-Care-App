package com.lph.selfcareapp.menu.Chat;

public interface ResponseCallback {
    void onResponse (String response);
    void onError (Throwable throwable);
}
