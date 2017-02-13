package com.pavel.controller;

import com.pavel.constants.HttpStatus;
import com.pavel.constants.PagesPath;
import com.pavel.controller.interfaces.HttpResponseHandler;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ResponseHandler implements HttpResponseHandler {

    @Override
    public byte[] doGet(String url) throws IOException {
        String path = HttpParser.getPath(url);
        if (checkPath(path)) {
            byte[] document = createDocument(path);
            byte[] headers = createHeaders(HttpStatus.STATUS_200.getConstant(), document.length);
            return createResponse(headers, document);
        } else {
            byte[] document = createDocument(PagesPath.NOT_FOUND);
            byte[] headers = createHeaders(HttpStatus.STATUS_404.getConstant(), document.length);
            return createResponse(headers, document);
        }
    }

    @Override
    public byte[] doPost(String url, InputStream input) throws IOException {
        String path = HttpParser.getPath(url);
        getInputValues(input);
        //Map <String, String> values = HttpParser.getValuePost(url);
        return doGet(url);
    }

    private void getInputValues(InputStream input) throws IOException {
        /*BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String string;
        while (true) {
            char ch = (char) reader.read();
            System.out.println(ch);
            if (ch == null || string.isEmpty()) {
                break;
            }
        }*/
    }

    @Override
    public void doHead() {

    }

    private byte[] createHeaders(String status, int length) {
        String responseHeader =
                "HTTP/1.1 " + status + "\r\n" +
                        "Date: " + createDate() + "\r\n" +
                        "Server: Http Server\r\n" +
                        "Content-Length: " + length + "\r\n" +
                        "Connection: keep-alive\r\n\r\n";
        return responseHeader.getBytes();
    }

    public byte[] createDocument(String path) throws IOException {
        File file = new File(path);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent;
    }

    private String createDate(){
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormatLocal.parse( dateFormatGmt.format(new Date()) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date);
    }

    private byte[] createResponse(byte[] headers, byte[] document) {
        int hLength = headers.length;
        int dLength = document.length;
        byte[] response = new byte[hLength + dLength];
        System.arraycopy(headers, 0, response, 0, hLength);
        System.arraycopy(document, 0, response, hLength, dLength);
        return response;
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
