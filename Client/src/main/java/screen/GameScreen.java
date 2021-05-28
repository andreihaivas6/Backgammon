package screen;

import item.Button;
import item.Dice;
import item.Item;
import processing.core.PApplet;

public class GameScreen extends Screen {
    private Board board;
    private Dice dice1, dice2;

    public GameScreen(PApplet processing) {
        super(processing);
        board = new Board(processing);
        dice1 = new Dice(processing, 160, 325);
        dice2 = new Dice(processing, 235, 325);
    }

    @Override
    public void show() {
        hover = false;
        board.show();
        dice1.show();
        dice2.show();
        hoverButtons();
        selectCursor();
    }

    public void rollDices() {
        dice1.rollTheDice();
        dice2.rollTheDice();
    }

    public boolean verifyRoll() {
        for(Item item : items) {
            if(item instanceof Button && ((Button) item).getLabel().getText().equals("Press")) {
                if(((Button) item).hasClicked()) {
                    for (int i = 0; i < 10; ++i) {
                        System.out.println(1);
                    }
                }
            }
        }

        return true;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Dice getDice1() {
        return dice1;
    }

    public void setDice1(Dice dice1) {
        this.dice1 = dice1;
    }

    public Dice getDice2() {
        return dice2;
    }

    public void setDice2(Dice dice2) {
        this.dice2 = dice2;
    }
}
