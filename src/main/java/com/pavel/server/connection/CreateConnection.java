package com.pavel.server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CreateConnection extends Thread {
    private ServerSocket serverSocket;
    public static final int PORT = 8080;

    public CreateConnection() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {
        if (serverSocket != null) {
            while (true) {
                Socket clientSocket = null;
                ClientSession clientSession = null;
                new Thread(clientSession).start();
            }
        }
    }
}
