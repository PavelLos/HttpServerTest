package com.pavel.server;


import com.pavel.constants.HttpMethod;
import com.pavel.controller.RequestHandler;
import com.pavel.controller.ResponseHandler;
import com.pavel.view.ServerWindow;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpServer {
    private ResponseHandler responseHandler;
    private RequestHandler requestHandler;

    private static Logger log = Logger.getLogger(HttpServer.class);

    private byte[] response;
    private boolean correctResponse;

    public HttpServer() {
        correctResponse = false;
    }

    public void httpMethod(InputStream input) throws IOException {
        requestHandler = new RequestHandler(input);
        if (requestHandler.isCorrectRequest()) {
            log.info("Client request: " + requestHandler.getMethod() + " " + requestHandler.getUrl());
            //ServerWindow.getInstance().printInfo("Client request: " + requestHandler.getInputRequest());
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
        correctResponse= true;
    }


    private void doPost() throws IOException {
        response = responseHandler.createPostResponse(requestHandler.getUrl(),
                requestHandler.getRequestParametersToString());
        correctResponse= true;

    }


    private void doHead() throws IOException {
        response = responseHandler.createHeadResponse(requestHandler.getUrl());
        correctResponse= true;

    }

    public void sendResponse(OutputStream output) throws IOException {
        if(correctResponse) {
            output.write(response);
            output.flush();
            correctResponse = false;
        }
    }
}
