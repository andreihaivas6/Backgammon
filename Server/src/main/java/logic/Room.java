package logic;

public class Room {
    private String code;

    private User player1, player2;
    private String message;

    private boolean gameStarted = false;
    private int turn = 0;
    private boolean diceRolled = false;


    public Room(User player1, String code) {
        player1.setIndex(0);
        this.player1 = player1;
        this.code = code;
    }

    public void addSecondPlayer(User player) {
        player.setIndex(1);
        player2 = player;
        gameStarted = true;

    }

    public String getMessage() {
        String result = new String(message);
        message = "wait";
        return result;
    }

    public void changeTurn() {
        turn = (turn + 1) % 2;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDiceRolled() {
        return diceRolled;
    }

    public void setDiceRolled(boolean diceRolled) {
        this.diceRolled = diceRolled;
    }

}
