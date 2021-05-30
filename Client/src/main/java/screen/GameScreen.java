package screen;

import item.Dice;
import item.Triangle;
import processing.DrawUtil;
import processing.Main;
import processing.core.PApplet;

import java.awt.*;

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
        if (Main.casaPlina) {
            processing.fill(Color.red.getRGB());
            processing.rect(500 - 35, 315, 420, 50 + 20);
        }
    }

    public void rollDices() {
        dice1.rollTheDice();
        dice2.rollTheDice();
        if (dice1.getValoare() == dice2.getValoare()) {
            dice1.setDisponibil(2);
            dice2.setDisponibil(2);
            if (Main.exampleHome || Main.casaPlina) {
                rollDices();
            }
        } else {
            dice1.setDisponibil(1);
            dice2.setDisponibil(1);
        }
    }

    public void redTriangles() {
        if (Main.indexPlayer == 0) {
            int val = Main.triangleClicked - dice1.getValoare();
            if (val >= 1 && val <= 24 && DrawUtil.mutareZarOk(dice1, val)) {
                board.getTriangles().get(val).setRed(true);
            }
            val = Main.triangleClicked - dice2.getValoare();
            if (val >= 1 && val <= 24 && DrawUtil.mutareZarOk(dice2, val)) {
                board.getTriangles().get(val).setRed(true);
            }
        } else {
            int val = Main.triangleClicked + dice1.getValoare();
            if (val >= 1 && val <= 24 && DrawUtil.mutareZarOk(dice1, val)) {
                board.getTriangles().get(val).setRed(true);
            }
            val = Main.triangleClicked + dice2.getValoare();
            if (val >= 1 && val <= 24 && DrawUtil.mutareZarOk(dice2, val)) {
                board.getTriangles().get(val).setRed(true);
            }
        }
    }

    public void redTrianglesMancate() {
        triangleRedWithDice(dice1);
        triangleRedWithDice(dice2);
    }

    private void triangleRedWithDice(Dice dice2) {
        int val;
        if (Main.indexPlayer == 0) {
            val = 24 - dice2.getValoare() + 1;
        } else {
            val = dice2.getValoare();
        }
        if (dice2.getDisponibil() > 0) {
            Triangle triangle = board.getTriangleByValoare(val);
            if (triangle.getIndexPlayer() == Main.indexPlayer || triangle.getIndexPlayer() == -1 ||
                    (triangle.getIndexPlayer() == (Main.indexPlayer + 1) % 2 && triangle.getNumberPieces() == 1)) {
                triangle.setRed(true);
            }
        }
    }

    public void clearRedTriangles() {
        for (Triangle triangle : board.getTriangles()) {
            triangle.setRed(false);
        }
    }

    public int getDisponibility() {
        return dice1.getDisponibil() + dice2.getDisponibil();
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
