package com.pavel.server.connection;

import com.pavel.view.ServerWindow;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CreateConnection extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public static final int PORT = 8080;

    private static final Logger log = Logger.getLogger(CreateConnection.class);

    public CreateConnection() {
        serverSocket = null;
        clientSocket = null;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            log.info("Server socket created in PORT: " + PORT);
        } catch (IOException e) {
            log.error("Client socket connection error");
            ServerWindow.getInstance().printInfo("Client socket connection error");
        }
        if (serverSocket != null) {
            ServerWindow.getInstance().printInfo("Server is running");
            while (!serverSocket.isClosed()) {
                clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    log.info("Client connection : " + clientSocket.getInetAddress());
                    ServerWindow.getInstance().printInfo("Client connection : " + clientSocket.getInetAddress() + "\r\n");
                    ClientSession clientSession = new ClientSession(clientSocket);
                    clientSession.start();
                } catch (IOException e) {
                    log.error("Client socket connection error");
                    ServerWindow.getInstance().printInfo("Client socket connection error");
                }
            }
        }
    }


    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                if (clientSocket != null)
                    clientSocket.close();
                log.info("Server is stopped");
                ServerWindow.getInstance().printInfo("Server is stopped");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
