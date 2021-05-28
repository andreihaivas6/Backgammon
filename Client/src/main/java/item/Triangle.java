package item;

import processing.core.PApplet;
import processing.core.PImage;

public class Triangle extends Item {
    private int valoare; // 1 -> 24 si inca 2 pentru piesele mancate
    private int indexPlayer; // 0, 1 sau -1 daca nu le-a luat nimeni inca
    private int numberPieces = 0;

    private PImage imgPieceWhite, imgPieceBlack;

    public Triangle(PApplet processing, int x, int y, int valoare) {
        super(processing, x, y, 0, 0);
        this.valoare = valoare;
        this.x = x;

        imgPieceWhite = processing.loadImage("img/pieceWhite.png");
        imgPieceBlack = processing.loadImage("img/pieceBlack.png");

        initTriangle();
    }

    private void initTriangle() {
        for (int el : new int[]{6, 12, 13, 19}) {
            if (valoare == el) {
                numberPieces = 5;
            }
        }
        if (valoare == 8 || valoare == 17) {
            numberPieces = 3;
        }
        if (valoare == 1 || valoare == 24) {
            numberPieces = 2;
        }

        for (int el : new int[]{6, 8, 13, 24, 26}) {
            if (valoare == el) {
                indexPlayer = 0;
            }
        }
        for (int el : new int[]{1, 12, 17, 1, 25}) {
            if (valoare == el) {
                indexPlayer = 1;
            }
        }
    }

    @Override
    public void show() {
//        if(!selected)
        if (valoare % 2 == 1) {
            processing.fill(255);
        } else {
            processing.fill(0);
        }
//        else fill(#D80000);
        if (valoare <= 12 || valoare == 25) {
            processing.triangle(x, y, x + 70, y, x + 35, y + 300);
        } else if (valoare <= 24 || valoare == 26) {
            processing.triangle(x, y, x + 70, y, x + 35, y - 300);
        }

        showPieces();
    }

    private void showPieces() {
        int counterPieces = 0;
        int numarPiesePeNivel = 5; // pentru a arata ca o piramida cand sunt prea multe piese.

        while (counterPieces < numberPieces) {
            int yAux = y - 5;
            if ((12 < valoare && valoare <= 24) || valoare == 26) {
                yAux -= 60;
            }

            if (valoare <= 12 || valoare == 25) {
                yAux += 30 * (5 - numarPiesePeNivel);
            } else {
                yAux -= 30 * (5 - numarPiesePeNivel);
            }

            for (int i = 1; i <= numarPiesePeNivel && counterPieces < numberPieces; i++) {
                counterPieces++;
                if (indexPlayer == 0) {
                    processing.image(imgPieceWhite, x, yAux, 70, 70);
                } else {
                    processing.image(imgPieceBlack, x, yAux, 70, 70);
                }
                if (valoare <= 12 || valoare == 25) {
                    yAux += 60;
                }
                else {
                    yAux -= 60;
                }
            }
            numarPiesePeNivel--;
        }
    }
}
