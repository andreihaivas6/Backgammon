package processing;

import item.Dice;
import item.Triangle;
import processing.core.PApplet;
import screen.GameScreen;

import java.awt.*;
import java.io.IOException;

public class DrawUtil {
    private static GameScreen gameScreen;
    private static int dice1, dice2;
    private static int indexPlayer;

    public static void init(GameScreen gameScreen1, int dice11, int dice21, int indexPlayer1) {
        gameScreen = gameScreen1;
        dice1 = dice11;
        dice2 = dice21;
        indexPlayer = indexPlayer1;
    }

    public static boolean aiPiesaMancata() {
        if (indexPlayer == 0) {
            return gameScreen.getBoard().getTriangles().get(26).getNumberPieces() > 0;
        } else {
            return gameScreen.getBoard().getTriangles().get(25).getNumberPieces() > 0;
        }
    }

    public static boolean aiLocSaPuiPiesaMancata() {
        if (indexPlayer == 0) {
            return ((gameScreen.getBoard().getTriangles().get(24 - dice1 + 1).getIndexPlayer() != 1 ||
                    (gameScreen.getBoard().getTriangles().get(24 - dice1 + 1).getIndexPlayer() == 1 &&
                            gameScreen.getBoard().getTriangles().get(24 - dice1 + 1).getNumberPieces() == 1)) &&
                    gameScreen.getDice1().getDisponibil() > 0)
                    ||
                    ((gameScreen.getBoard().getTriangles().get(24 - dice2 + 1).getIndexPlayer() != 1 ||
                            (gameScreen.getBoard().getTriangles().get(24 - dice2 + 1).getIndexPlayer() == 1 &&
                                    gameScreen.getBoard().getTriangles().get(24 - dice2 + 1).getNumberPieces() == 1)) &&
                            gameScreen.getDice2().getDisponibil() > 0);
        } else {
            return ((gameScreen.getBoard().getTriangles().get(dice1).getIndexPlayer() != 0 ||
                    (gameScreen.getBoard().getTriangles().get(dice1).getIndexPlayer() == 0 &&
                            gameScreen.getBoard().getTriangles().get(dice1).getNumberPieces() == 1))
                    && gameScreen.getDice1().getDisponibil() > 0)
                    ||
                    ((gameScreen.getBoard().getTriangles().get(dice2).getIndexPlayer() != 1 ||
                            (gameScreen.getBoard().getTriangles().get(dice2).getIndexPlayer() == 1 &&
                                    gameScreen.getBoard().getTriangles().get(dice2).getNumberPieces() == 1))
                            && gameScreen.getDice2().getDisponibil() > 0);
        }
    }

    public static void aiCasaPlina() {
        if(Main.casaPlina) {
            return;
        }
        int count = 0;
        if (indexPlayer == 0) {
            for (int i = 7; i <= 24; ++i) {
                if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == 0) {
                    count += gameScreen.getBoard().getTriangles().get(i).getNumberPieces();
                }
            }
        } else {
            for (int i = 1; i <= 18; ++i) {
                if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == 1) {
                    count += gameScreen.getBoard().getTriangles().get(i).getNumberPieces();
                }
            }
        }
        Main.casaPlina = count == 0;
    }

    public static boolean aiZarBunPentruAScoate() {
        if (indexPlayer == 0) {
            return (gameScreen.getBoard().getTriangles().get(dice1).getNumberPieces() > 0
                    && gameScreen.getDice1().getDisponibil() > 0)
                    ||
                    (gameScreen.getBoard().getTriangles().get(dice2).getNumberPieces() > 0
                            && gameScreen.getDice2().getDisponibil() > 0);
        } else {
            return (gameScreen.getBoard().getTriangles().get(24 - dice1 + 1).getNumberPieces() > 0
                    && gameScreen.getDice1().getDisponibil() > 0)
                    ||
                    (gameScreen.getBoard().getTriangles().get(24 - dice1 + 1).getNumberPieces() > 0
                            && gameScreen.getDice2().getDisponibil() > 0);
        }
    }

    public static void scoatePiesa(PApplet processing) throws IOException, InterruptedException {
        if(Main.triangleClicked == -1) {
            return;
        }
        boolean ok = false;
        if(indexPlayer == 0) {
            if (gameScreen.getDice1().getValoare() == Main.triangleClicked && gameScreen.getDice1().getDisponibil() > 0) {
                gameScreen.getBoard().getTriangles().get(Main.triangleClicked).minusOnePiece();
                gameScreen.getDice1().minusOneDisponibility();
                sendOutRequest(Main.triangleClicked, gameScreen.getDice1().getValoare());
                ok = true;
            } else if (gameScreen.getDice2().getValoare() == Main.triangleClicked && gameScreen.getDice2().getDisponibil() > 0) {
                gameScreen.getBoard().getTriangles().get(Main.triangleClicked).minusOnePiece();
                gameScreen.getDice2().minusOneDisponibility();
                sendOutRequest(Main.triangleClicked, gameScreen.getDice2().getValoare());
                ok = true;
            }
        } else {
            if (gameScreen.getDice1().getValoare() == 25 - Main.triangleClicked && gameScreen.getDice1().getDisponibil() > 0) {
                gameScreen.getBoard().getTriangles().get(25 - Main.triangleClicked).minusOnePiece();
                gameScreen.getDice1().minusOneDisponibility();
                sendOutRequest(Main.triangleClicked, gameScreen.getDice1().getValoare());
                ok = true;
            } else if (gameScreen.getDice2().getValoare() == 25 - Main.triangleClicked && gameScreen.getDice2().getDisponibil() > 0) {
                gameScreen.getBoard().getTriangles().get(25 - Main.triangleClicked).minusOnePiece();
                gameScreen.getDice2().minusOneDisponibility();
                sendOutRequest(Main.triangleClicked, gameScreen.getDice2().getValoare());
                ok = true;
            }
        }
        if(ok) {
            Main.triangleClicked = -1;
            if (numarPieseInCasa() == 0) {
                Main.gameOver = true;
                Main.winner = true;
                Main.sendRequest("update/over");
            }
        }
        reparaMareGreseala();
    }

    private static void reparaMareGreseala() {
        if(indexPlayer == 0) {
            for(int i = 1; i <= 6; ++i) {
                gameScreen.getBoard().getTriangleByValoare(i).setIndexPlayer(0);
            }
        } else {
            for(int i = 19; i <= 24; ++i) {
                gameScreen.getBoard().getTriangleByValoare(i).setIndexPlayer(1);
            }
        }
    }

    public static int numarPieseInCasa() {
        int count = 0;
        if (indexPlayer == 0) {
            for (int i = 1; i <= 6; ++i) {
                if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == 0) {
                    count += gameScreen.getBoard().getTriangles().get(i).getNumberPieces();
                }
            }
        } else {
            for (int i = 19; i <= 24; ++i) {
                if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == 1) {
                    count += gameScreen.getBoard().getTriangles().get(i).getNumberPieces();
                }
            }
        }
        return count;
    }

    public static void mutaPiesa() throws IOException, InterruptedException {
        for (int i = 1; i <= 24; ++i) {
            verificareTriunghiOk(i);
            if (Main.triangleClicked == -1) {
                gameScreen.clearRedTriangles();
                break;
            }
        }
    }

    public static void punePiesaMancata() throws IOException, InterruptedException {
        if (indexPlayer == 0) {
            for (int i = 19; i <= 24; ++i) {
                verificareTriunghiOkPiesaMancata(i);
                if (Main.triangleClicked == -1) {
                    gameScreen.clearRedTriangles();
                    break;
                }
            }
        } else {
            for (int i = 1; i <= 6; ++i) {
                verificareTriunghiOkPiesaMancata(i);
                if (Main.triangleClicked == -1) {
                    gameScreen.clearRedTriangles();
                    break;
                }
            }
        }
    }

    private static void verificareTriunghiOk(int i) throws IOException, InterruptedException {
        if (gameScreen.getBoard().getTriangles().get(i).isHover() && mutareZarOk(gameScreen.getDice1(), i)) {
            gameScreen.getBoard().getTriangles().get(i).plusOnePiece();
            gameScreen.getDice1().minusOneDisponibility();
            sendMoveRequest(Main.triangleClicked, i);
            Main.triangleClicked = -1;
        } else if (gameScreen.getBoard().getTriangles().get(i).isHover() && mutareZarOk(gameScreen.getDice2(), i)) {
            gameScreen.getBoard().getTriangles().get(i).plusOnePiece();
            gameScreen.getDice2().minusOneDisponibility();
            sendMoveRequest(Main.triangleClicked, i);
            Main.triangleClicked = -1;
        }
    }

    private static void verificareTriunghiOkPiesaMancata(int i) throws IOException, InterruptedException {
        if (gameScreen.getBoard().getTriangles().get(i).isHover() && mutareZarOkPiesaMancata(gameScreen.getDice1(), i)) {
            gameScreen.getBoard().getTriangles().get(i).plusOnePiece();
            gameScreen.getDice1().minusOneDisponibility();
            sendMoveRequest(Main.triangleClicked, i);
            Main.triangleClicked = -1;
        } else if (gameScreen.getBoard().getTriangles().get(i).isHover() && mutareZarOkPiesaMancata(gameScreen.getDice2(), i)) {
            gameScreen.getBoard().getTriangles().get(i).plusOnePiece();
            gameScreen.getDice2().minusOneDisponibility();
            sendMoveRequest(Main.triangleClicked, i);
            Main.triangleClicked = -1;
        }
    }

    private static void sendMoveRequest(int pos1, int pos2) throws IOException, InterruptedException {
        DrawUtil.aiCasaPlina();
        String request = "update/move " + pos1 + " " + pos2;
        if (Main.gameScreen.getDisponibility() == 0) {
            request += " end";
            Main.wait = true;
            Main.diceRolled = false;
        }
        Main.sendRequest(request);
    }

    private static void sendOutRequest(int pos1, int diceVal) throws IOException, InterruptedException {
        String request = "update/out " + pos1;
        gameScreen.getBoard().getTriangles().get(diceVal).plusOne(); // cv bug f dobios
        if (Main.gameScreen.getDisponibility() == 0) {
            request += " end";
            Main.wait = true;
            Main.diceRolled = false;
        }
        Main.sendRequest(request);
    }

    public static boolean mutareZarOk(Dice dice, int triangleValue) {
        Triangle triangleToMoveOn = gameScreen.getBoard().getTriangles().get(triangleValue);
        if (indexPlayer == 0) {
            return Main.triangleClicked - dice.getValoare() == triangleValue &&
                    dice.getDisponibil() > 0 &&
                    ((triangleToMoveOn.getIndexPlayer() != 1) ||
                            (triangleToMoveOn.getIndexPlayer() == 1 &&
                                    triangleToMoveOn.getNumberPieces() == 1));
        } else {
            return Main.triangleClicked + dice.getValoare() == triangleValue &&
                    dice.getDisponibil() > 0 &&
                    ((triangleToMoveOn.getIndexPlayer() != 0) ||
                            (triangleToMoveOn.getIndexPlayer() == 0 &&
                                    triangleToMoveOn.getNumberPieces() == 1));
        }
    }

    public static boolean mutareZarOkPiesaMancata(Dice dice, int triangleValue) {
        Triangle triangleToMoveOn = gameScreen.getBoard().getTriangles().get(triangleValue);
        if (indexPlayer == 0) {
            return triangleValue == 24 - dice.getValoare() + 1 &&
                    dice.getDisponibil() > 0 &&
                    ((triangleToMoveOn.getIndexPlayer() != 1) ||
                            (triangleToMoveOn.getIndexPlayer() == 1 &&
                                    triangleToMoveOn.getNumberPieces() == 1));
        } else {
            return triangleValue == dice.getValoare() &&
                    dice.getDisponibil() > 0 &&
                    ((triangleToMoveOn.getIndexPlayer() != 0) ||
                            (triangleToMoveOn.getIndexPlayer() == 0 &&
                                    triangleToMoveOn.getNumberPieces() == 1));

        }
    }

    public static boolean potiMutaPiesa() {
        if (indexPlayer == 0) {
            for (int i = 1; i <= 24; i++) {
                if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == 0) {
                    if (gameScreen.getDice1().getDisponibil() > 0 && i - dice1 >= 1 && i - dice1 <= 24 &&
                            (gameScreen.getBoard().getTriangles().get(i - dice1).getIndexPlayer() != 1 ||
                                    (gameScreen.getBoard().getTriangles().get(i - dice1).getIndexPlayer() == 1 &&
                                            gameScreen.getBoard().getTriangles().get(i - dice1).getNumberPieces() == 1)
                            )) {
                        return true;
                    } else if (gameScreen.getDice2().getDisponibil() > 0 && i - dice2 >= 1 && i - dice2 <= 24 &&
                            (gameScreen.getBoard().getTriangles().get(i - dice2).getIndexPlayer() != 1 ||
                                    (gameScreen.getBoard().getTriangles().get(i - dice2).getIndexPlayer() == 1 &&
                                            gameScreen.getBoard().getTriangles().get(i - dice2).getNumberPieces() == 1)
                            )) {
                        return true;
                    }
                }
            }
        } else {
            for (int i = 1; i <= 24; i++) {
                if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == 0) {
                    if (gameScreen.getDice1().getDisponibil() > 0 && i + dice1 >= 1 && i + dice1 <= 24 &&
                            (gameScreen.getBoard().getTriangles().get(dice1 + i).getIndexPlayer() != 1 ||
                                    (gameScreen.getBoard().getTriangles().get(dice1 + i).getIndexPlayer() == 1 &&
                                            gameScreen.getBoard().getTriangles().get(dice1 + i).getNumberPieces() == 1)
                            )) {
                        return true;
                    } else if (gameScreen.getDice2().getDisponibil() > 0 && i + dice2 >= 1 && i + dice2 <= 24 &&
                            (gameScreen.getBoard().getTriangles().get(dice2 + i).getIndexPlayer() != 1 ||
                                    (gameScreen.getBoard().getTriangles().get(dice2 + i).getIndexPlayer() == 1 &&
                                            gameScreen.getBoard().getTriangles().get(dice2 + i).getNumberPieces() == 1)
                            )) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean selecteazaPiesaMancata() {
        gameScreen.redTrianglesMancate();
        if (indexPlayer == 0) {
            if (Main.triangleClicked == 26) {
                return true;
            } else if (gameScreen.getBoard().getTriangles().get(26).hasClicked()) {
                Main.triangleClicked = 26;
                gameScreen.getBoard().getTriangles().get(26).minusOnePiece();
                return true;
            }
        } else {
            if (Main.triangleClicked == 25) {
                return true;
            } else if (gameScreen.getBoard().getTriangles().get(25).hasClicked()) {
                Main.triangleClicked = 25;
                gameScreen.getBoard().getTriangles().get(25).minusOnePiece();
                return true;
            }
        }
        return false;
    }

    public static boolean selecteazaPiesa() {
        if (Main.triangleClicked != -1) {
            System.out.println("t: " + Main.triangleClicked);
            return true;
        }
        for (int i = 1; i <= 24; ++i) {
            if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == indexPlayer &&
                    gameScreen.getBoard().getTriangles().get(i).hasClicked()) {
                Main.triangleClicked = i;
                System.out.println(i);
                gameScreen.getBoard().getTriangles().get(i).minusOnePiece();
                gameScreen.redTriangles();
                return true;
            }
        }
        return false;
    }

    public static void schimbaJucator() throws IOException, InterruptedException {
        Main.wait = true;
        Main.diceRolled = false;
        Main.sendRequest("update/end");
    }
}
