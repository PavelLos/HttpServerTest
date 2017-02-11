package com.pavel.server;


import com.pavel.constants.HttpMethod;
import com.pavel.handler.ResponseHandler;

import java.io.OutputStream;

public class HttpResponse {
    private ResponseHandler responseHandler;

    public HttpResponse(OutputStream outputStream) {
        responseHandler = new ResponseHandler();
    }

    public String httpMethod(String method, String url) {
        if (method.equals(HttpMethod.GET.getMethod())) {
            String response = responseHandler.doGet(url);
            return response;
        }
        if (method.equals(HttpMethod.POST.getMethod())) {

        }
        if (method.equals(HttpMethod.HEAD.getMethod())) {

        }
        return method;
    }
}
