package com.pavel.handler.interfaces;


public interface HttpResponseHandler {

    String doGet(String url);

    void doPost();

    void doHead();

}
