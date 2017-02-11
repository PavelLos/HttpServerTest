package com.pavel.server;

import com.pavel.handler.RequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;


public class ClientSession extends Thread {

    private Socket clientSocket;
    private RequestHandler requestHandler;
    private HttpResponse httpResponse;
    private List<String> inputRequest;

    private InputStream input;
    private OutputStream output;

    public ClientSession(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        input = clientSocket.getInputStream();
        output = clientSocket.getOutputStream();
        requestHandler = new RequestHandler();
        httpResponse = new HttpResponse(output);
    }

    public void run() {
        try {
            inputRequest = requestHandler.getInputRequest(input);
            //requestHandler.getRequestHeaders(inputRequest);
            String response = httpResponse.httpMethod(
                    requestHandler.getMethod(inputRequest.get(0)),
                    requestHandler.getRequestURI(inputRequest.get(0))
            );
            System.out.println(response);
            output.write(response.getBytes());
            output.flush();

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
