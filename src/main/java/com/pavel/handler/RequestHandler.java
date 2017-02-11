package com.pavel.handler;

import com.pavel.handler.interfaces.HttpRequestHandler;
import com.pavel.server.HttpParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RequestHandler implements HttpRequestHandler {

    @Override
    public List getInputRequest(final InputStream input) throws IOException {
        List inputRequest = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String string;
        while (true) {
            string = reader.readLine();
            System.out.println(string);
            if (string == null || string.isEmpty()) {
                break;
            }
            inputRequest.add(string);
        }

        return inputRequest;
    }

    @Override
    public String getRequestURI(String string) {
        return HttpParser.getUrl(string);
    }

    @Override
    public Map<String, String> getRequestHeaders(List<String> input) throws IOException {
        Map httpInfo = new HashMap<String, String>();
        for (String request : input) {
            if (HttpParser.getSplitRequest(request) != null) {
                httpInfo.putAll(HttpParser.getSplitRequest(request));
            }
        }
        return httpInfo;
    }

    @Override
    public String getRequestHeader(Map headers, String nameOfHeader) throws IOException {
        if (headers.get(nameOfHeader) != null) {
            return (String) headers.get(nameOfHeader);
        }
        return "";
    }

    @Override
    public String getMethod(String string) {
        return HttpParser.getMethod(string);
    }


}
