package com.pavel.controller;

import com.pavel.constants.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RequestHandler {
    private List<String> inputRequest;
    private Map<String, String> httpInfo;
    private List<String> requestParameters;
    private String url;
    private String method;

    public RequestHandler(final InputStream input) {
        requestParameters = new ArrayList<>();
        inputRequest = new ArrayList<>();
        httpInfo = new HashMap<>();


        getInputRequest(input);

        url = getRequestURI();
        method = getRequestMethod();
        requestParameters = getRequestParameters();
    }

    private void getInputRequest(final InputStream input) {
        int size = 0;
        try {
            size = input.available();
            byte[] inputBytes = new byte[size];
            input.read(inputBytes);
            String str = new String(inputBytes);
            String[] strings = str.split("\\r\\n");
            for (String s : strings) {
                inputRequest.add(s);
            }
            inputRequest.remove("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getRequestURI() {
        if (inputRequest.size() == 0)
            return "";
        return HttpParser.getUrl(inputRequest.get(0));
    }


    public Map<String, String> getRequestHeaders(List<String> input) throws IOException {
        for (String request : input) {
            if (HttpParser.getSplitRequest(request) != null) {
                httpInfo.putAll(HttpParser.getSplitRequest(request));
            }
        }
        return httpInfo;
    }

    private String getRequestHeader(String nameOfHeader) throws IOException {
        if (httpInfo.get(nameOfHeader) != null) {
            return (String) httpInfo.get(nameOfHeader);
        }
        return "";
    }


    private String getRequestMethod() {
        return HttpParser.getMethod(inputRequest.get(0));
    }

    private List<String> getRequestParameters() {
        if (method.equals(HttpMethod.GET.getMethod()))
            requestParameters = HttpParser.getValues(url);
        if (method.equals(HttpMethod.POST.getMethod()))
            requestParameters = HttpParser.getValues(inputRequest.get(inputRequest.size() - 1));
        return requestParameters;
    }

    public String getRequestParametersToString() {
        StringBuilder parameters = new StringBuilder();
        for (int i = 0; i < requestParameters.size(); i++) {
            parameters.append(requestParameters);
        }
        return parameters.toString();
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public List<String> getInputRequest() {
        return inputRequest;
    }
}
