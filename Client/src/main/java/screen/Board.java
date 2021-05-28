package screen;

import item.Item;
import item.Triangle;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private PImage imgBackground;
    private final PApplet processing;
    private final List<Item> triangles = new ArrayList<>();

    public Board(PApplet processing) {
        this.processing = processing;
        imgBackground = processing.loadImage("img/background.jpg");
        initBoard();
    }

    private void initBoard() {
        triangles.clear();

        int x = 885;
        for (int i = 1; i <= 12; i++) {
            x -= 70;
            triangles.add(new Triangle(processing, x, 15, i));
            if (i == 6) {
                x -= 30;
            }
        }

        x = 15;
        for (int i = 13; i <= 24; i++) {
            triangles.add(new Triangle(processing, x, 685, i));
            x += 70;
            if (i == 18) {
                x += 30;
            }
        }

        triangles.add(new Triangle(processing, 415, 350, 25));
        triangles.add(new Triangle(processing, 415, 350, 26));
    }

    public void show() {
        processing.image(imgBackground, 0, 0, processing.width, processing.height);
        processing.fill(processing.color(210, 105, 30));
        processing.rect(0, 0, 15, processing.height);
        processing.rect(435, 0, 30, processing.height);
        processing.rect(900 - 15, 0, 15, processing.height);
        processing.rect(0, 0, processing.width, 15);
        processing.rect(0, 700 - 15, processing.width, 15);
        processing.line(450, 15, 450, processing.height - 15);

        for (Item triangle : triangles) {
            triangle.show();
        }
    }

    public PApplet getProcessing() {
        return processing;
    }

    public List<Item> getTriangles() {
        return triangles;
    }
}
