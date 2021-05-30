package item;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

public class Dice extends Item {
    private int valoare;
    private int disponibil = 0;
    private PImage[] imgDice = new PImage[7];

    public Dice(PApplet processing, int x, int y) {
        super(processing, x, y, 50, 50);
        this.valoare = 6;
        for (int i = 1; i <= 6; ++i) {
            imgDice[i] = processing.loadImage("img/dice" + i + ".png");
        }
    }

    @Override
    public void show() {
        processing.noStroke();
        if (disponibil == 1) {
            processing.fill(Color.yellow.getRGB());
            processing.rect(x - 5, y - 5, 60, 60);
        } else if (disponibil == 2) {
            processing.fill(Color.orange.getRGB());
            processing.rect(x - 5, y - 5, 60, 60);
        }

        processing.fill(255);
        processing.rect(x, y, 50, 50);
        processing.image(imgDice[valoare], x, y, 50, 50);
        processing.stroke(0);
    }

    public void rollTheDice() {
        valoare = (int) (Math.random() * 6) + 1;
    }

    public void minusOneDisponibility() {
        if (disponibil > 0) {
            disponibil--;
        }
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

    public int getDisponibil() {
        return disponibil;
    }

    public void setDisponibil(int disponibil) {
        this.disponibil = disponibil;
    }
}
