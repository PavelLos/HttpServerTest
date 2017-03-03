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


    public CreateConnection() {
        serverSocket = null;
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
            while (!serverSocket.isClosed()) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    log.info("Client connection : " + clientSocket.getInetAddress());
                    ServerWindow.getInstance().printInfo("Client connection : " + clientSocket.getInetAddress());
                    ClientSession clientSession = new ClientSession(clientSocket);
                    clientSession.start();
                } catch (IOException e) {
                    log.error("Client socket connection error");
                    ServerWindow.getInstance().printInfo("Client socket connection error");
                }
            }
        }
    }

    /*@Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            log.info("Server socket created in PORT: " + PORT);
            if (serverSocket != null) {
                while (!serverSocket.isClosed()) {
                    Socket clientSocket = null;
                    log.info("Server is running now");
                    clientSocket = serverSocket.accept();
                    log.info("Client connection : " + clientSocket.getInetAddress());
                    ServerWindow.getInstance().printInfo("Client connection : " + clientSocket.getInetAddress());
                    socketList.add(clientSocket);
                    ClientSession clientSession = new ClientSession(clientSocket);
                    clientSession.start();
                    *//*try {

                    } catch (IOException e) {
                        if (serverSocket.isClosed()) {
                            log.error("Client socket connection error");
                            ServerWindow.getInstance().printInfo("Client socket connection error");
                        }
                    }*//*
                *//*finally {
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                            log.info("Client is disconnected: " + clientSocket.getInetAddress());
                        } catch (IOException e) {
                        } finally {
                            clientSocket = null;
                        }
                    }
                }*//*
                }
            }
        } catch (IOException e) {
            log.error("Server socket not created in PORT: " + PORT);
        } *//*finally {
            stopServer();
        }*//*
    }*/

    /*public void startServer() {
        if (serverSocket != null) {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = null;
                if (isStop) {
                    this.notify();
                    isStop = false;
                }
                try {
                    log.info("Server is running now");
                    clientSocket = serverSocket.accept();
                    log.info("Client connection : " + clientSocket.getInetAddress());
                    ServerWindow.getInstance().printInfo("Client connection : " + clientSocket.getInetAddress());
                    socketList.add(clientSocket);
                    ClientSession clientSession = new ClientSession(clientSocket);
                    clientSession.start();
                } catch (IOException e) {
                    if (serverSocket.isClosed()) {
                        log.error("Client socket connection error");
                        ServerWindow.getInstance().printInfo("Client socket connection error");
                    }
                }
                *//*finally {
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                            log.info("Client is disconnected: " + clientSocket.getInetAddress());
                        } catch (IOException e) {
                        } finally {
                            clientSocket = null;
                        }
                    }
                }*//*
            }
        }
    }*/

    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                log.info("Server is stopped");
            } catch (IOException e) {
                e.printStackTrace();
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
