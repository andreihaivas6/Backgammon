package item.board;

import item.Item;
import processing.Main;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.Objects;

public class Triangle extends Item {
    private int valoare; // 1 -> 24 si inca 2 pentru piesele mancate
    private int indexPlayer = -1; // 0, 1 sau -1 daca nu le-a luat nimeni inca
    private int numberPieces = 0;
    private boolean red = false;

    private PImage imgPieceWhite, imgPieceBlack;

    public Triangle(PApplet processing, int x, int y, int valoare) {
        super(processing, x, y, 0, 0);
        this.valoare = valoare;
        this.x = x;

        imgPieceWhite = processing.loadImage("img/pieceWhite.png");
        imgPieceBlack = processing.loadImage("img/pieceBlack.png");

        if(Main.exampleHome) {
            initTriangleTestHome();
        } else {
            initTriangle();
        }
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
        for (int el : new int[]{1, 12, 17, 19, 25}) {
            if (valoare == el) {
                indexPlayer = 1;
            }
        }
    }

    private void initTriangleTestHome() {
        for(int i = 1; i <= 6; ++i) {
            if(valoare == i) {
                numberPieces = 5;
                indexPlayer = 0;
            }
        }

        for(int i = 19; i <= 24; ++i) {
            if(valoare == i) {
                numberPieces = 5;
                indexPlayer = 1;
            }
        }

        if(valoare == 7) {
            numberPieces = 1;
            indexPlayer = 0;
        }
        if(valoare == 18) {
            numberPieces = 1;
            indexPlayer = 1;
        }
    }

    @Override
    public void show() {
        if(valoare > 24) {
            showPieces();
            return;
        }
        if (red && Main.triangleClicked != -1) {
            processing.fill(Color.red.getRGB());
        } else if (valoare % 2 == 1) {
            processing.fill(255);
        } else {
            processing.fill(0);
        }
        if (!(valoare <= 12 || valoare == 25)) {
            processing.triangle(x, y, x + 70, y, x + 35, y + 300);
        } else {
            processing.triangle(x, y, x + 70, y, x + 35, y - 300);
        }

        showPieces();
    }

    private void showPieces() {
        int counterPieces = 0;
        int numarPiesePeNivel = 5; // pentru a arata ca o piramida cand sunt prea multe piese.

        while (counterPieces < numberPieces) {
            int yAux = y - 5;
            if (!((12 < valoare && valoare <= 24) || valoare == 26)) {
                yAux -= 60;
            }

            if (valoare <= 12 || valoare == 25) {
                yAux -= 30 * (5 - numarPiesePeNivel);
            } else {
                yAux += 30 * (5 - numarPiesePeNivel);
            }

            for (int i = 1; i <= numarPiesePeNivel && counterPieces < numberPieces; i++) {
                counterPieces++;
                if (indexPlayer == 0) {
                    processing.image(imgPieceWhite, x, yAux, 70, 70);
                } else {
                    processing.image(imgPieceBlack, x, yAux, 70, 70);
                }
                if (valoare <= 12 || valoare == 25) {
                    yAux -= 60;
                } else {
                    yAux += 60;
                }
            }
            numarPiesePeNivel--;
        }
    }

    public boolean hasClicked() {
        if (this.isHover() && processing.mousePressed && Main.mouseIsReleased) {
            Main.mouseIsReleased = false;
            return true;
        }
        return false;
    }

    public boolean isHover() {
        if (valoare <= 12 || valoare == 25) {
            return x <= processing.mouseX && processing.mouseX <= x + 70
                    && y - 300 <= processing.mouseY && processing.mouseY <= y;
        } else {
            return x <= processing.mouseX && processing.mouseX <= x + 70
                    && y <= processing.mouseY && processing.mouseY <= y + 300;
        }
    }

    public void minusOnePiece() {
//        if (numberPieces > 0) {
            numberPieces--;
//        }
        if (numberPieces == 0 && valoare <= 24) {
            indexPlayer = -1;
        }
    }

    public void plusOne() {
        numberPieces++;
    }

    public void plusOnePiece() {
        if (Main.indexPlayer != indexPlayer && numberPieces == 1) { // se mananac piesa
            indexPlayer = Main.indexPlayer;
            if (indexPlayer == 0) {
                Main.gameScreen.getBoard().getTriangles().get(25).plusOne();
            } else {
                Main.gameScreen.getBoard().getTriangles().get(26).plusOne();
            }
        } else {
            numberPieces++;
            if (valoare <= 24) {
                indexPlayer = Main.indexPlayer;
            }
        }
    }

    public void plusOnePieceEnemy() {
        if (Main.indexPlayer == indexPlayer && numberPieces == 1) { // se mananac piesa
            if (indexPlayer == 1) {
                Main.gameScreen.getBoard().getTriangles().get(25).plusOne();
            } else {
                Main.gameScreen.getBoard().getTriangles().get(26).plusOne();
            }
            indexPlayer = (Main.indexPlayer + 1) % 2;
        } else {
            numberPieces++;
            if (valoare <= 24) {
                indexPlayer = (Main.indexPlayer + 1) % 2;
            }
        }
    }


    public int getValoare() {
        return valoare;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public int getNumberPieces() {
        return numberPieces;
    }

    public void setNumberPieces(int numberPieces) {
        this.numberPieces = numberPieces;
    }

    public PImage getImgPieceWhite() {
        return imgPieceWhite;
    }

    public void setImgPieceWhite(PImage imgPieceWhite) {
        this.imgPieceWhite = imgPieceWhite;
    }

    public PImage getImgPieceBlack() {
        return imgPieceBlack;
    }

    public void setImgPieceBlack(PImage imgPieceBlack) {
        this.imgPieceBlack = imgPieceBlack;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "processing=" + processing +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", valoare=" + valoare +
                ", indexPlayer=" + indexPlayer +
                ", numberPieces=" + numberPieces +
                ", red=" + red +
                ", imgPieceWhite=" + imgPieceWhite +
                ", imgPieceBlack=" + imgPieceBlack +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return valoare == triangle.valoare && indexPlayer == triangle.indexPlayer && numberPieces == triangle.numberPieces && red == triangle.red && Objects.equals(imgPieceWhite, triangle.imgPieceWhite) && Objects.equals(imgPieceBlack, triangle.imgPieceBlack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valoare, indexPlayer, numberPieces, red, imgPieceWhite, imgPieceBlack);
    }
}
