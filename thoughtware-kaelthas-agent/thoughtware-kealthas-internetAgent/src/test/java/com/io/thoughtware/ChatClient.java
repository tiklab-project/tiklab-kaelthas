package com.io.thoughtware;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost"; // 服务器地址
    private static final int SERVER_PORT = 12345; // 服务器端口
    private PrintWriter out;

    public static void main(String[] args) {
        new ChatClient().start();
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new IncomingReader(socket)).start();
            Scanner scanner = new Scanner(System.in);
            String message;

            while (scanner.hasNextLine()) {
                message = scanner.nextLine();
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IncomingReader implements Runnable {
        private BufferedReader in;

        public IncomingReader(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("接收到消息: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
