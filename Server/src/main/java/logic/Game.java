package logic;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<User> usersNotLoggedIn = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();

    public void Game() {}

    public void addUser(User user) {
        usersNotLoggedIn.add(user);
    }

    public void removeUser(User user) {
        usersNotLoggedIn.remove(user);
    }

    public boolean createRoom(User user, String code) {
        if(code.length() < 4) {
            return false;
        }

        for(Room room : rooms) {
            if (room.getCode().equals(code)) {
                return false;
            }
        }

        removeUser(user);
        rooms.add(new Room(user, code));
        return true;
    }

    public boolean joinRoom(User user, String code) {
        for(Room room : rooms) {
            if(room.getCode().equals(code) && room.getPlayer2() == null) {
                removeUser(user);
                room.addSecondPlayer(user);
                room.setMessage("start");
                return true;
            }
        }
        return false;
    }

    public void removeRoomWithUser(User user) {
        for(Room room : rooms) {
            if(user.equals(room.getPlayer1()) || user.equals(room.getPlayer2())) {
                usersNotLoggedIn.add(room.getPlayer1());
                usersNotLoggedIn.add(room.getPlayer2());
                rooms.remove(room);
                break;
            }
        }
    }

    public Room getRoomWithUser(User user) {
        for(Room room : rooms) {
            if (user.equals(room.getPlayer1()) || user.equals(room.getPlayer2())) {
                return room;
            }
        }
        return null;
    }

}
