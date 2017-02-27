package com.pavel.server.connection;

import com.pavel.view.ServerWindow;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class CreateConnection extends Thread {
    private ServerSocket serverSocket;
    public static final int PORT = 8080;

    private static CreateConnection instance = null;
    private static final Logger log = Logger.getLogger(CreateConnection.class);

    private List<Socket> socketList;

    public CreateConnection() {
        serverSocket = null;
        socketList = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            log.info("Server socket created in PORT: " + PORT);
            startServer();
        } catch (IOException e) {
            log.error("Server socket not created in PORT: " + PORT);
        } finally {
            stopServer();
        }
    }

    public void startServer() {
        if (serverSocket != null) {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    log.info("Client connection : " + clientSocket.getInetAddress());
                    ServerWindow.getInstance().printInfo("Client connection : " + clientSocket.getInetAddress());
                    socketList.add(clientSocket);
                    ClientSession clientSession = new ClientSession(clientSocket);
                    clientSession.start();
                } catch (IOException e) {
                    log.error("Client socket connection error");
                    ServerWindow.getInstance().printInfo("Client socket connection error");
                } /*finally {
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                            log.info("Client is disconnected: " + clientSocket.getInetAddress());
                        } catch (IOException e) {
                        } finally {
                            clientSocket = null;
                        }
                    }
                }*/
            }
        }
    }

    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                log.info("Server is stopped");
                ServerWindow.getInstance().printInfo("Server is stopped");
            } catch (IOException ignore) {
            } finally {
                serverSocket = null;
            }
        }
    }

    public static CreateConnection getInstance() {
        if (instance == null) {
            instance = new CreateConnection();
        }
        return instance;
    }
}
