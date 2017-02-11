package com.pavel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CreateConnection extends Thread {
    private ServerSocket serverSocket;
    public static final int PORT = 8080;

    public CreateConnection() throws IOException {
        serverSocket = new ServerSocket(PORT);
        start();
    }

    private void startServer() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientSession clientSession = new ClientSession(clientSocket);
            new Thread(clientSession).start();
        }
    }

    @Override
    public void run() {
        if(serverSocket!=null){
            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClientSession clientSession = null;
                try {
                    clientSession = new ClientSession(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new Thread(clientSession).start();
            }
        }
    }
}
