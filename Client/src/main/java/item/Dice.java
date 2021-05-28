package item;

import processing.core.PApplet;
import processing.core.PImage;

public class Dice extends Item{
    private int valoare;
    private PImage[] imgDice = new PImage[7];

    public Dice(PApplet processing, int x, int y) {
        super(processing, x, y, 50, 50);
        this.valoare = 6;
        for(int i = 1; i <= 6; ++i) {
            imgDice[i] = processing.loadImage("img/dice" + i + ".png");
        }
    }

    @Override
    public void show() {
        processing.fill(255);
        processing.rect(x - 1, y - 1 , 51, 51);
        processing.image(imgDice[valoare], x, y, 50, 50);
    }

    public void rollTheDice() {
        valoare = (int)(Math.random() * 6) + 1;
    }

    public int getValoare() {
        return valoare;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    public PImage[] getImgDice() {
        return imgDice;
    }

    public void setImgDice(PImage[] imgDice) {
        this.imgDice = imgDice;
    }
}
