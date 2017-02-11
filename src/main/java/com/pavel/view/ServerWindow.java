package com.pavel.view;

import javax.swing.*;
import java.awt.*;


public class ServerWindow extends JFrame {

    private JTextArea textArea;
    private static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

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
        /*JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            try {
                ServerConnection.getInstance().startServer();
            } catch (IOException e1) {
                e1.printStackTrace();

            }
        });
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            try {
                //ServerConnection.getInstance().stopServer();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        Box box = Box.createHorizontalBox();
        box.add(startButton);
        box.add(Box.createHorizontalGlue());
        box.add(stopButton);
        add(box, BorderLayout.NORTH);*/
    }


    public void printInfo(StringBuilder message) {
        textArea.setText(String.valueOf(message));
    }
}
