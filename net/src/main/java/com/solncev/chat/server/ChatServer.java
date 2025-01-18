package com.solncev.chat.server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();
    ;

    static class Client implements Runnable {

        private BufferedReader in;
        private BufferedWriter out;
        private ChatServer server;

        public Client(BufferedReader in, BufferedWriter out, ChatServer server) {
            this.in = in;
            this.out = out;
            this.server = server;
        }

        public static void main(String[] args) {
            ChatServer server = new ChatServer();
            server.start();
        }


        @Override
        public void run() {
            try {
                while (true) {
                    String message = in.readLine();
                    server.sendMessage(message, this);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void start() {
        try {
            serverSocket = new ServerSocket(5555);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                Client client = new Client(in, out, this);

                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String message, Client client) {
        for (Client c : clients) {
            if (c.equals(client)) {
                continue;
            }
            try {
                c.out.write(message + "\n");
                c.out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
