package com.pavel.server;


import com.pavel.constants.HttpMethod;
import com.pavel.controller.RequestHandler;
import com.pavel.controller.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpServer {
    private ResponseHandler responseHandler;
    private RequestHandler requestHandler;

    private byte[] response;

    public HttpServer() {
    }

    public void httpMethod(InputStream input) throws IOException {
        requestHandler = new RequestHandler(input);
        if (requestHandler.getInputRequest().size() != 0) {
            responseHandler = new ResponseHandler();
            String method = requestHandler.getMethod();
            if (method.equals(HttpMethod.GET.getMethod())) {
                doGet();
            }
            if (method.equals(HttpMethod.POST.getMethod())) {
                doPost();
            }
            if (method.equals(HttpMethod.HEAD.getMethod())) {
                doHead();
            }
        }
    }

    private void doGet() throws IOException {
        response = responseHandler.createGetResponse(requestHandler.getUrl());

    }


    private void doPost() throws IOException {
        response = responseHandler.createPostResponse(requestHandler.getUrl(),
                requestHandler.getRequestParametersToString());
    }


    private void doHead() throws IOException {
        response = responseHandler.createHeadResponse(requestHandler.getUrl());
    }

    public void sendResponse(OutputStream output) throws IOException {
        output.write(response);
        output.flush();
    }
}
