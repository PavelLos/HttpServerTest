package com.pavel.server.connection;

import com.pavel.server.HttpServer;
import com.pavel.view.ServerWindow;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ClientSession extends Thread {

    private Socket clientSocket;
    private InputStream input;
    private OutputStream output;
    private HttpServer httpServer;
    private static Logger log = Logger.getLogger(ClientSession.class);

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
            if (input != null && output != null) {
                httpServer = new HttpServer();
                httpServer.httpMethod(input);
                httpServer.sendResponse(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
                if (clientSocket != null) {
                    log.info("Client: " + clientSocket.getInetAddress() + " is disconnect");
                    ServerWindow.getInstance().printInfo("Client: " + clientSocket.getInetAddress() + " is disconnect\r\n");
                    clientSocket.close();
                }
            } catch (IOException e) {
                log.error("Client: " + clientSocket.getInetAddress() + " can't disconnect");
            }
        }
    }


}
