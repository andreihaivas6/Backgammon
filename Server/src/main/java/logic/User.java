package logic;

import javax.persistence.*;
import java.net.Socket;

public class User {
    private int index; // 0, 1: daca e primul sau al doilea jucator din camera pentru a putea juca in ordine
    private boolean loggedIn = false;

    private Long id;
    private String username;
    private String password;

    private Socket socket;

    public User(Socket socket) {
        this.socket = socket;
    }

    public User(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
