package com.pavel.controller;

import com.pavel.constants.HttpStatus;
import com.pavel.constants.PagesPath;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ResponseHandler {


    public byte[] createResponse(String url) throws IOException {
        String path = HttpParser.getPath(url);
        byte[] document;
        byte[] headers;
        if (checkPath(path)) {
            document = createDocument(path);
            headers = createHeaders(HttpStatus.STATUS_200.getConstant(),
                    document.length,
                    getContentType(path));
        } else {
            document = createDocument(PagesPath.NOT_FOUND);
            headers = createHeaders(HttpStatus.STATUS_404.getConstant(),
                    document.length,
                    getContentType(path));
        }
        return getResponseByte(headers, document);
    }

    public byte[] createResponse(String url, String documentText) throws IOException {
        String path = HttpParser.getPath(url);
        byte[] document;
        byte[] headers;
        if (checkPath(path)) {
            document = createDocument(path, documentText);
            headers = createHeaders(HttpStatus.STATUS_200.getConstant(),
                    document.length,
                    getContentType(path));
        } else {
            document = createDocument(PagesPath.NOT_FOUND);
            headers = createHeaders(HttpStatus.STATUS_404.getConstant(),
                    document.length,
                    getContentType(path));
        }
        return getResponseByte(headers, document);
    }


    private byte[] createHeaders(String status, int length, String contentType) {
        String responseHeader =
                "HTTP/1.1 " + status + "\r\n" +
                        "Date: " + createDate() + "\r\n" +
                        "Server: Http Server\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Length: " + length + "\r\n" +
                        "Connection: keep-alive\r\n\r\n";
        return responseHeader.getBytes();
    }

    private byte[] createDocument(String path) throws IOException {
        File file = new File(path);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent;
    }

    private byte[] createDocument(String path, String documentText) {
        StringBuilder document = new StringBuilder();
        byte[] fileContent = document.toString().getBytes();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String fileString;
            while (true) {
                fileString = reader.readLine();
                if (fileString == null || fileString.isEmpty())
                    break;
                document.append(fileString);
                if (fileString.equals("<body>"))
                    document.append(documentText);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileContent = document.toString().getBytes();
        return fileContent;
    }

    private String createDate() {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date);
    }

    private byte[] getResponseByte(byte[] headers, byte[] document) {
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

    private String getContentType(String path) {
        int point = path.lastIndexOf(".");
        String contentType = "text/html";
        if (point > 0) {
            String ext = path.substring(point);
            if (ext.equalsIgnoreCase("htm"))
                contentType = "text/html";
            else if (ext.equalsIgnoreCase("gif"))
                contentType = "image/gif";
            else if (ext.equalsIgnoreCase("jpg"))
                contentType = "image/jpeg";
            else if (ext.equalsIgnoreCase("jpeg"))
                contentType = "image/jpeg";
            else if (ext.equalsIgnoreCase("bmp"))
                contentType = "image/x-xbitmap";
            else if (ext.equalsIgnoreCase("css"))
                contentType = "text/css";
            else if (ext.equalsIgnoreCase("ico"))
                contentType = "image/ico";
        }
        return contentType;
    }
}
