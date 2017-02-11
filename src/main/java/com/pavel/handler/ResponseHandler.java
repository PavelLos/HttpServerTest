package com.pavel.handler;

import com.pavel.constants.HttpStatus;
import com.pavel.constants.PagesPath;
import com.pavel.handler.interfaces.HttpResponseHandler;
import com.pavel.server.HttpParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ResponseHandler implements HttpResponseHandler {

    @Override
    public String doGet(String url) {
        StringBuffer response = new StringBuffer();
        String path = HttpParser.getPath(url);
        if (checkPath(path)) {
            StringBuffer document = createDocument(path);
            StringBuffer headers = createHeaders(HttpStatus.STATUS_200.getConstant(), document.length());
            response.append(headers).append(document);
            //sendResponse(output, response);
        } else {
            StringBuffer document = createDocument(PagesPath.NOT_FOUND);
            StringBuffer headers = createHeaders(HttpStatus.STATUS_404.getConstant(), document.length());
            response.append(headers).append(document);
            //sendResponse(output, response);
        }
        return String.valueOf(response);
    }

    @Override
    public void doPost() {

    }

    @Override
    public void doHead() {

    }

    /*private void sendResponse(OutputStream output, StringBuilder string) {
        String response = String.valueOf(string);
        try {
            output.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private StringBuffer createHeaders(String status, int length) {
        StringBuffer responseHeader = new StringBuffer();
        responseHeader.append("HTTP/1.1 " + status + "\r\n");
        responseHeader.append("Date: " + createDate() + "\r\n");
        responseHeader.append("Server: Http Server\r\n");
        //responseHeader.append("Content-Type: text/html\r\n");
        responseHeader.append("Content-Length: " + length + "\r\n");
        responseHeader.append("Connection: keep-alive\r\n\r\n");
        return responseHeader;
    }


    private String createDate() {
        DateFormat date = DateFormat.getTimeInstance();
        date.setTimeZone(TimeZone.getTimeZone("GMT"));
        return date.format(new Date());
    }

    private StringBuffer createDocument(String path) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(path));
            StringBuffer responseFile = new StringBuffer();
            while (file.ready()) {
                responseFile.append(file.readLine());
            }
            return responseFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkPath(String path) {
        try (FileReader file = new FileReader(path)) {
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;

        }
    }
}
