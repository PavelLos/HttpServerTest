package com.pavel.server;


import com.pavel.constants.HttpMethod;
import com.pavel.controller.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpResponse {
    private ResponseHandler responseHandler;

    public HttpResponse(OutputStream outputStream) {
        responseHandler = new ResponseHandler();
    }

    public byte[] httpMethod(String method, String url, InputStream input) throws IOException {
        if (method.equals(HttpMethod.GET.getMethod())) {
            return responseHandler.doGet(url);
        }
        if (method.equals(HttpMethod.POST.getMethod())) {
            responseHandler.doPost(url, input);
        }
        if (method.equals(HttpMethod.HEAD.getMethod())) {

        }
        return null;
    }

    public void sendResponse(OutputStream output, byte [] response) throws IOException {
        output.write(response);
        output.flush();
    }
}
