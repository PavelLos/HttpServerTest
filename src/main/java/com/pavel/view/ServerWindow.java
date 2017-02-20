package com.pavel.view;

import com.pavel.server.connection.CreateConnection;

import javax.swing.*;
import java.awt.*;


public class ServerWindow extends JFrame {

    private JTextArea textArea;
    private static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height / 3;
    private static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width / 4;

    private boolean serverIsRunning = false;

    public ServerWindow() {
        createTextField();
        createButtons();
        createFrame();
    }

    private void createFrame() {
        setSize(HEIGHT, WIDTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createTextField() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea));
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void createButtons() {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startServer());
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stopServer());
        JButton cleanLogButton = new JButton("Clean Log");
        cleanLogButton.addActionListener(e -> cleanLog());

        Box box = Box.createHorizontalBox();
        box.add(startButton);
        box.add(Box.createHorizontalGlue());
        box.add(stopButton);
        box.add(Box.createHorizontalGlue());
        box.add(cleanLogButton);
        add(box, BorderLayout.NORTH);
    }

    private void startServer() {
        if (!serverIsRunning) {
            CreateConnection.getInstance().start();
            serverIsRunning = true;
        }
    }

    private void stopServer() {
        if (serverIsRunning) {
            CreateConnection.getInstance().stopServer();
            serverIsRunning = false;
        }
    }

    private void cleanLog() {

    }

    public void printInfo(StringBuilder message) {
        textArea.setText(String.valueOf(message));
    }
}
