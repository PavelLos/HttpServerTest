package com.pavel.server.connection;

import com.pavel.server.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ClientSession extends Thread {

    private Socket clientSocket;
    private InputStream input;
    private OutputStream output;
    private HttpServer httpServer;

    //private static Lo

    public ClientSession(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            httpServer = new HttpServer();
            httpServer.httpMethod(input);
            httpServer.sendResponse(output);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
