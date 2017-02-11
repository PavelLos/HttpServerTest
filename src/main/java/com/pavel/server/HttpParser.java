package com.pavel.server;

import com.pavel.constants.PagesPath;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class HttpParser {
    private static final String REQUEST_HEADER_SPLIT = ":\\s";
    private static final String REQUEST_HEADER = "^[A-Za-z-]+:\\s.*$";
    private static final String REQUEST_URL = "^(GET|POST|HEAD).+";
    private static final String REQUEST_METHOD = "(GET|POST|HEAD).+";

    private static final Pattern patternSplit = Pattern.compile(REQUEST_HEADER_SPLIT);
    private static final Pattern patternHeader = Pattern.compile(REQUEST_HEADER);
    private static final Pattern patternURL = Pattern.compile(REQUEST_URL);
    private static final Pattern patternMETHOD = Pattern.compile(REQUEST_METHOD);

    public static Map getSplitRequest(final String string) {
        if (patternHeader.matcher(string).matches()) {
            String[] strings = patternSplit.split(string);
            Map httpInfo = new HashMap<String, String>();
            httpInfo.put(strings[0], strings[1]);
            return httpInfo;
        }
        return null;
    }

    public static String getUrl(final String string) {
        if (patternURL.matcher(string).matches()) {
            return string.substring(4, string.indexOf(" ", 5));
        }
        return "";
    }

    public static String getMethod(final String string) {
        if (patternMETHOD.matcher(string).matches()) {
            return string.substring(0, string.indexOf(" "));
        }
        return null;
    }

    public static String getPath(String url) {
        String path = PagesPath.PATH;
        if (url.equals("/")) {
            return PagesPath.DEFAULT_PATH;
        }
        char a;
        for (int i = 0; i < url.length(); i++) {
            a = url.charAt(i);
            if (a == '/')
                path = path + File.separator;
            else
                path = path + a;
        }
        return path;
    }
}
