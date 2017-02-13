package com.pavel.controller.interfaces;


import java.io.IOException;
import java.io.InputStream;

public interface HttpResponseHandler {

    byte[] doGet(String url) throws IOException;

    byte[] doPost(String url, InputStream input) throws IOException;

    void doHead();

}
