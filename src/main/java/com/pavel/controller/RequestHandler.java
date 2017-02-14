package com.pavel.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class RequestHandler {
    private List<String> inputRequest;
    private Map<String, String> httpInfo;
    private Map<String, String> requestParameters;

    public RequestHandler(final InputStream input) {
        try {
            getInputRequest(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getInputRequest(final InputStream input) throws IOException {
        inputRequest = new ArrayList<String>();
        int size = input.available();
        byte[] inputBytes = new byte[size];
        input.read(inputBytes);
        String str = new String(inputBytes);
        System.out.println(str);
        String[] strings = str.split("\\r\\n");
        Collections.addAll(inputRequest, strings);
        inputRequest.remove("");
        requestParameters = HttpParser.getValues(getRequestURI());
    }


    public String getRequestURI() {
        return HttpParser.getUrl(inputRequest.get(0));
    }


    public Map<String, String> getRequestHeaders(List<String> input) throws IOException {
        httpInfo = new HashMap();
        for (String request : input) {
            if (HttpParser.getSplitRequest(request) != null) {
                httpInfo.putAll(HttpParser.getSplitRequest(request));
            }
        }
        return httpInfo;
    }

    public String getRequestHeader(String nameOfHeader) throws IOException {
        if (httpInfo.get(nameOfHeader) != null) {
            return (String) httpInfo.get(nameOfHeader);
        }
        return "";
    }


    public String getMethod() {
        return HttpParser.getMethod(inputRequest.get(0));
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public String getRequestParametersToString() {
        StringBuilder parameters = new StringBuilder();
        for (int i = 0; i < requestParameters.size(); i++) {
            String value = requestParameters.get(i);
            parameters.append(value);
        }
        return parameters.toString();
    }
}
