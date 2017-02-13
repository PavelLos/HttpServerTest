package com.pavel.controller.interfaces;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface HttpRequestHandler {

    List getInputRequest(InputStream input) throws IOException;

    String getRequestURI(String string);

    Map<String, String> getRequestHeaders(List<String> input) throws IOException;

    String getRequestHeader(Map headers, String nameOfHeader) throws IOException;

    String getMethod(String string);
}
