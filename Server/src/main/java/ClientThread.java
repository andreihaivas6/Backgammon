import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientThread extends Thread {
    private Game game;
    private User currentUser;
    private Room currentRoom;
    private Socket socket;

    public ClientThread (User currentUser, Game game) {
        game.addUser(currentUser);
        this.currentUser = currentUser;
        this.game = game;
        socket = currentUser.getSocket();
    }

    public void run () {
        try {
            do {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();

                String response = getResponse(request);

                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(response);
                out.flush();

            }while (true);
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println (e);
            }
        }
    }

    private String getResponse(String request) {
        String command = request.split("/")[0];
        String arg = "";
        if(request.split("/").length > 1) {
            arg = request.split("/")[1];
        }

        return switch (command) {
            case "Join Room" -> joinRoom(arg);
            case "Create Room" -> createRoom(arg);
            case "PvC Easy" -> pvcEasy();
            case "PvC Hard" -> pvcHard();
            case "Exit" -> myExit();
            case "Quit" -> myQuit();
            case "Game status" -> status(arg);
            case "update" -> update(arg);
            default -> "Bad request";
        };
    }

    private String joinRoom(String code) {
        if(game.joinRoom(currentUser, code)) {
            currentRoom = game.getRoomWithUser(currentUser);
            return "Joined Room";
        }
        return "Bad code";
    }

    private String createRoom(String code) {
        if(game.createRoom(currentUser, code)) {
            currentRoom = game.getRoomWithUser(currentUser);
            return "Created Room";
        }
        return "Bad code";
    }

    private String pvcEasy() {
        return "pvcEasy";
    }

    private String pvcHard() {
        return "pvcHard";
    }

    private String myExit() {
        return "exit";
    }

    private String myQuit() {
        game.removeRoomWithUser(currentUser);
        currentRoom = null;
        return "quit";
    }

    private String status(String turn) {
        if(currentRoom.getPlayer2() == null/* || currentRoom.getTurn() != Integer.parseInt(turn)*/) {
            return "wait";
        }
//        } else if(currentUser.getIndex() != currentRoom.getTurn()) {
//            return "wait " + currentRoom.getMessage();
//        }
        return currentRoom.getMessage();
    }

    private String update(String request) {
        System.out.println("Set message: " + request);
        currentRoom.setMessage(request);
        if(request.endsWith("end")) {
            currentRoom.changeTurn();
        }
        return "Succesful update";
    }

}