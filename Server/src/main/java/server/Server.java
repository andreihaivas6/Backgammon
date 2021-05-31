package server;

import logic.Game;
import logic.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 8888;
    public Server() throws IOException {
        Game game = new Game();

        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                new ClientThread(new User(socket), game).start();
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        }
    }
}