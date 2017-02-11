package com.pavel.handler.interfaces;


import java.io.IOException;

public interface HttpResponseHandler {

    byte[] doGet(String url) throws IOException;

    void doPost();

    void doHead();

}
