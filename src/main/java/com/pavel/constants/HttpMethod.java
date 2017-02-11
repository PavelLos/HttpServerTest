package com.pavel.constants;


public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    HEAD("HEAD");

    private String method;

    HttpMethod(String string) {
        method = string;
    }

    public String getMethod() {
        return method;
    }
}
