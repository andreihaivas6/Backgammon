package processing.util;

import item.board.Dice;
import item.board.Triangle;
import processing.Main;
import processing.core.PApplet;
import screen.GameScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

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
                    ((gameScreen.getBoard().getTriangles().get(dice2).getIndexPlayer() != 0 ||
                            (gameScreen.getBoard().getTriangles().get(dice2).getIndexPlayer() == 0 &&
                                    gameScreen.getBoard().getTriangles().get(dice2).getNumberPieces() == 1))
                            && gameScreen.getDice2().getDisponibil() > 0);
        }
    }

    public static void aiCasaPlina() {
        if (Main.casaPlina) {
            return;
        }
        Main.casaPlina = verificareCasaPlina() == 0;
    }

    public static boolean aiCasaPlinaRobot() {
        return verificareCasaPlina() == 0;
    }

    private static int verificareCasaPlina() {
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
        return count;
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
        if (Main.triangleClicked == -1) {
            return;
        }
        boolean ok = false;
        if (indexPlayer == 0) {
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
        if (ok) {
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
        System.out.println("repara");
        if (indexPlayer == 0) {
            for (int i = 1; i <= 6; ++i) {
                gameScreen.getBoard().getTriangleByValoare(i).setIndexPlayer(0);
            }
        } else {
            for (int i = 19; i <= 24; ++i) {
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
                gameScreen.show();
                break;
            }
        }
        gameScreen.show();
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
        endOfTurn(request);
    }

    private static void sendOutRequest(int pos1, int diceVal) throws IOException, InterruptedException {
        String request = "update/out " + pos1;
        gameScreen.getBoard().getTriangles().get(diceVal).plusOne(); // cv bug f dobios
        endOfTurn(request);
    }

    private static void endOfTurn(String request) throws IOException, InterruptedException {
        if (Main.gameScreen.getDisponibility() == 0) {
            request += " end";
            Main.wait = true;
            Main.diceRolled = false;
            if (Main.playVsComputer) {
                if (Main.computerTurn) {
                    indexPlayer = 0;
                } else {
                    indexPlayer = 1;
                }
                Main.computerTurn = !Main.computerTurn;
            }
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
            //System.out.println("t: " + Main.triangleClicked);
            return true;
        }
        for (int i = 1; i <= 24; ++i) {
            if (gameScreen.getBoard().getTriangles().get(i).getIndexPlayer() == indexPlayer &&
                    gameScreen.getBoard().getTriangles().get(i).hasClicked()) {
                Main.triangleClicked = i;
                //System.out.println(i);
                gameScreen.getBoard().getTriangles().get(i).minusOnePiece();
                gameScreen.redTriangles();
                return true;
            }
        }
        return false;
    }

    public static void schimbaJucator() throws IOException, InterruptedException {
        Main.gameScreen.show();
        Main.wait = true;
        Main.diceRolled = false;
        Main.sendRequest("update/end");
        if (Main.playVsComputer) {
            Main.computerTurn = true;
            Main.indexPlayer = 1;
        }
    }

    public static void robotLogic() throws IOException, InterruptedException {
        Main.gameScreen.show();
        if (aiPiesaMancata()) {
            if (aiLocSaPuiPiesaMancata()) {
                punePiesaMancataRobot();
            } else {
                schimbaJucatorDinRobotInOm();
            }
        } else { // nu ai piesa mancata
            if (aiCasaPlinaRobot()) { // incepi sa scoti
                if (aiZarBunPentruAScoate()) {
                    scoatePiesaRobot();
                } else {
                    if (potiMutaPiesa()) {
                        mutaPiesaRobot();
                    } else {
                        schimbaJucatorDinRobotInOm();
                    }
                }
            } else {
                if (potiMutaPiesa() /*&& Main.triangleClicked != -1*/) {
                    mutaPiesaRobot();
                } else {
                    schimbaJucatorDinRobotInOm();
                }
            }
        }
        if (gameScreen.getDisponibility() == 0) {
            //System.out.println("O terminat robotu");
            Main.computerTurn = false;
            Main.triangleClicked = -1;
            Main.indexPlayer = 0;
            Main.wait = false;
            Main.diceRolled = false;
        }
    }

    private static void punePiesaMancataRobot() throws IOException, InterruptedException {
        // cautam toate posibilitatile sa puna piesa mancata
        // in functie de dificultate: ia random / cea mai buna
        // facem mutarea
        //System.out.println("Pune piesa mancata robot");
        List<MutareRobot> mutari = new ArrayList<>();

        for (int i = 1; i <= 6; ++i) {
            verificaTriunghiuriMancatRobot(mutari, i);
            if (Main.triangleClicked == -1) {
                gameScreen.clearRedTriangles();
            }
        }

        for (int j = 1; j <= 6; ++j) {
            Triangle triangle = gameScreen.getBoard().getTriangleByValoare(j);
            if (triangle.getIndexPlayer() == 0 && triangle.getNumberPieces() > 1) { // alt bug dobios
                int finalJ = j;
                mutari.removeIf(mutareRobot -> mutareRobot.getTriangleTo().getValoare() == finalJ);
            }
        }
        if(mutari.isEmpty()) {
            schimbaJucatorDinRobotInOm();
        }

        MutareRobot mutareAleasa;
        mutareAleasa = MutareRobot.robotAI(mutari, gameScreen, indexPlayer);

        // mutare 2 !!!
//        gameScreen.getBoard().getTriangleByValoare(mutareAleasa.getTriangleTo().getValoare() + 1).plusOnePieceEnemy();
        gameScreen.getBoard().getTriangleByValoare(mutareAleasa.getTriangleTo().getValoare() ).plusOnePieceEnemy();
        gameScreen.getBoard().getTriangleByValoare(25).minusOnePiece();
        mutareAleasa.getDice().minusOneDisponibility();
        sendMoveRequest(mutareAleasa.getTriangleFrom().getValoare(), mutareAleasa.getTriangleTo().getValoare());
        Main.triangleClicked = -1;
        gameScreen.show();
        sleep(1000);
    }

    private static void verificaTriunghiuriMancatRobot(List<MutareRobot> mutari, int i) {
        Triangle triangle = gameScreen.getBoard().getTriangleByValoare(i);
        if(gameScreen.getDice1().getDisponibil() > 0 &&
            gameScreen.getDice1().getValoare() == i &&
                (triangle.getIndexPlayer() != 0 ||
                        (triangle.getIndexPlayer() == 0
                        && triangle.getNumberPieces() == 1))) {
            mutari.add(new MutareRobot(gameScreen.getBoard().getTriangleByValoare(26),
                    triangle, gameScreen.getDice1()));
        }
        if(gameScreen.getDice2().getDisponibil() > 0 &&
                gameScreen.getDice2().getValoare() == i &&
                (triangle.getIndexPlayer() != 0 ||
                        (triangle.getIndexPlayer() == 0
                                && triangle.getNumberPieces() == 1))) {
            mutari.add(new MutareRobot(gameScreen.getBoard().getTriangleByValoare(26),
                    triangle, gameScreen.getDice2()));
        }
        //System.out.println("verifica triunghiuri mancat robot");
//        if (mutareZarOkPiesaMancata(gameScreen.getDice1(), i)) {
//            mutari.add(new MutareRobot(gameScreen.getBoard().getTriangleByValoare(26),
//                    gameScreen.getBoard().getTriangleByValoare(i), gameScreen.getDice1()));
//        }
//        if (mutareZarOkPiesaMancata(gameScreen.getDice2(), i)) {
//            mutari.add(new MutareRobot(gameScreen.getBoard().getTriangleByValoare(26),
//                    gameScreen.getBoard().getTriangleByValoare(i), gameScreen.getDice2()));
//        }

        if (Main.triangleClicked == -1) {
            gameScreen.clearRedTriangles();
        }
    }

    public static boolean mutareZarOkPiesaMancata2(Dice dice, int triangleValue) {
        Triangle triangleToMoveOn = gameScreen.getBoard().getTriangles().get(triangleValue);
        return triangleValue == dice.getValoare() &&
                dice.getDisponibil() > 0 &&
                ((triangleToMoveOn.getIndexPlayer() != 0) ||
                        (triangleToMoveOn.getIndexPlayer() == 0 &&
                                triangleToMoveOn.getNumberPieces() == 1));
    }


    private static void verificaTriunghiuriRobot(int triangleValue, List<MutareRobot> mutari, int i) {
        if (Main.triangleClicked == -1) {
            return;
        }
        //System.out.println(Main.triangleClicked + " " + triangleValue);
        //System.out.println("verifica triunghiuri robot");
        //System.out.println("VAl zar 1: " + gameScreen.getDice1().getValoare());
        if (mutareZarOk(gameScreen.getDice1(), i)) {

            mutari.add(new MutareRobot(gameScreen.getBoard().getTriangleByValoare(triangleValue),
                    gameScreen.getBoard().getTriangleByValoare(i), gameScreen.getDice1()));
        }
        //System.out.println("VAl zar 2: " + gameScreen.getDice2().getValoare());
        if (mutareZarOk(gameScreen.getDice2(), i)) {

            mutari.add(new MutareRobot(gameScreen.getBoard().getTriangleByValoare(triangleValue),
                    gameScreen.getBoard().getTriangleByValoare(i), gameScreen.getDice2()));
        }
        if (Main.triangleClicked == -1) {
            gameScreen.clearRedTriangles();
        }
    }

    public static void schimbaJucatorDinRobotInOm() throws IOException, InterruptedException {
        Main.indexPlayer = 0;
        Main.computerTurn = false;
        Main.wait = false;
        Main.diceRolled = false;
        Main.sendRequest("update/end");
    }

    private static void mutaPiesaRobot() throws IOException, InterruptedException {
        // cautam toate posibilitatile sa puna piesa mancata
        // in functie de dificultate: ia random / cea mai buna
        // facem mutarea

        //System.out.println("Muta piesa robot");
        List<MutareRobot> mutari = new ArrayList<>();
        List<Triangle> robotTriangles = new ArrayList<>();// luam triunghiuri pe care avem piesa
        for (Triangle triangle : gameScreen.getBoard().getTriangles()) {
            if (triangle.getIndexPlayer() == 1 && triangle.getValoare() <= 24) {
                robotTriangles.add(triangle);
                //System.out.println("Triunghi posibil adaugat");
            }
        }

        for (int i = 1; i <= 24; ++i) {
            for (Triangle robotTriangle : robotTriangles) {
                Main.triangleClicked = robotTriangle.getValoare();
                verificaTriunghiuriRobot(robotTriangle.getValoare(), mutari, i);
                Main.triangleClicked = -1;
            }
            if (Main.triangleClicked == -1) {
                gameScreen.clearRedTriangles();
            }
        }

        MutareRobot mutareAleasa;
        mutareAleasa = MutareRobot.robotAI(mutari, gameScreen, indexPlayer);

        Main.gameScreen.show();
        mutareAleasa.getTriangleFrom().minusOnePiece();
        mutareAleasa.getTriangleTo().plusOnePieceEnemy();
        mutareAleasa.getDice().minusOneDisponibility();
        sendMoveRequest(mutareAleasa.getTriangleFrom().getValoare(), mutareAleasa.getTriangleTo().getValoare());
        sleep(1000);
        Main.triangleClicked = -1;
        Main.gameScreen.show();
    }

    private static void scoatePiesaRobot() throws InterruptedException {
        for (int i = 19; i <= 24; ++i) {
            if (gameScreen.getDice1().getValoare() == 25 - i && gameScreen.getDice1().getDisponibil() > 0) {
                gameScreen.show();
                gameScreen.getBoard().getTriangles().get(i).minusOnePiece();
                gameScreen.getDice1().minusOneDisponibility();
                return;
            } else if (gameScreen.getDice2().getValoare() == 25 - i && gameScreen.getDice2().getDisponibil() > 0) {
                gameScreen.show();
                gameScreen.getBoard().getTriangles().get(i).minusOnePiece();
                gameScreen.getDice2().minusOneDisponibility();
                return;
            }
        }

        if (gameScreen.getDisponibility() == 0) {
            Main.triangleClicked = -1;
            Main.computerTurn = false;
            Main.indexPlayer = 0;
            Main.wait = false;
            Main.diceRolled = false;
        }
        reparaMareGreseala();
    }
}
