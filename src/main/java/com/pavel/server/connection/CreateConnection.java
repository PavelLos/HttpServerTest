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
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (serverSocket != null) {
            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    ClientSession clientSession = new ClientSession(clientSocket);
                    clientSession.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
