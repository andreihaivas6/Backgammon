package processing;

import processing.core.PApplet;
import processing.util.DrawUtil;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Draw {
    private static PApplet processing;

    public static void draw(PApplet processing) throws IOException, InterruptedException {
        Draw.processing = processing;
        processing.background(Main.COLOR_BACKGROUND);
        processing.line(0, 0, processing.width, 0);

        if (Main.gameOver) {
            processing.fill(0);
            if (Main.winner) {
                processing.text("You won!", 300, 300);
            } else {
                processing.text("You lost!", 300, 300);
            }
        } else {
            switch (Main.currentScreen) {
                case 0 -> login();
                case 1 -> menu();
                case 2 -> game();
            }
        }
    }

    private static void login() throws IOException {
        Main.loginScreen.show();
        processing.fill(255);
        processing.rect(365, 220, 200, 80);
        processing.line(365 , 260, 365 + 200 , 260);
        processing.fill(0);
        processing.text(Main.request, 380, 250);

        String buttonClicked = Main.loginScreen.getClicked();
        if (!buttonClicked.equals("")) {
            String request = buttonClicked + "/" + Main.request.split("\n")[0] + "#" + Main.request.split("\n")[1];
//            System.out.println(request);

            Main.out.println(request);
            Main.request = "";

            Main.response = Main.in.readLine();
            takeActionInLogin(Main.response);
        }
    }

    private static void takeActionInLogin(String response) {
        switch (response) {
            case "exit":
                System.exit(1);
            case "log in":
                Main.currentScreen = 1;
                Main.request = "";
                break;
            case "new user created":
                Main.infoLogin.setText("Account created succesfully");
                break;
            case "not logged in":
                Main.infoLogin.setText("Bad account credentials");
                break;
            case "Account exists":
                Main.infoLogin.setText("Username already exists");
                break;
        }
    }

    private static void menu() throws IOException {
        Main.menuScreen.show();
        processing.text(Main.request, 390, 250);

        String buttonClicked = Main.menuScreen.getClicked();
        if (!buttonClicked.equals("")) {
            String request = buttonClicked + "/" + Main.request;
//            System.out.println("aici: " + request);

            Main.out.println(request);
            Main.request = "";

            Main.response = Main.in.readLine();
            takeActionInMenu(Main.response);
        }
    }

    private static void takeActionInMenu(String response) {
        //System.out.println("R: " + response);
        switch (response) {
            case "exit":
                System.exit(1);
                break;
            case "Bad code":
                Main.info.setText(Main.response);
                break;
            case "Created Room":
                Main.currentScreen = 2;
                Main.wait = true;
                Main.indexPlayer = 0;
                Main.gameScreen.getBoard().initBoard();
                break;
            case "Joined Room":
                Main.currentScreen = 2;
                Main.started = true;
                Main.wait = true;
                Main.indexPlayer = 1;
                Main.gameScreen.getBoard().initBoard();
                break;
            case "pvcEasy":
                Main.currentScreen = 2;
                Main.started = true;
                Main.wait = false;
                Main.indexPlayer = 0;
                Main.gameScreen.getBoard().initBoard();
                Main.vsComputeEasy = true;
                Main.playVsComputer = true;
                break;
            case "pvcHard":
                Main.currentScreen = 2;
                Main.started = true;
                Main.wait = false;
                Main.indexPlayer = 0;
                Main.gameScreen.getBoard().initBoard();
                Main.vsComputeEasy = false;
                Main.playVsComputer = true;
                break;
            default:
                break;
        }
    }

    private static void game() throws IOException, InterruptedException {
        Main.gameScreen.show();

        if (Main.computerTurn) {
            if (!Main.diceRolled) {
                Main.diceRolled = true;
                Main.gameScreen.rollDices();
                Main.gameScreen.getDice1().show();
                Main.gameScreen.getDice2().show();
                if (!Main.casaPlina) {
                    sleep(1000);
                }
                return;
            }
            DrawUtil.robotLogic();
        } else {
            switch (Main.gameScreen.getClicked()) {
                case "Quit":
                    Main.out.println("Quit");
                    Main.request = "";
                    Main.response = Main.in.readLine();
                    Main.currentScreen = 1;
                    Main.started = false;
                    break;
                case "Press":
                    if (!Main.diceRolled && !Main.wait) { //////////////////////////////////////////////////////////////////////// !!!Main.wait
                        Main.gameScreen.rollDices();
                        Main.diceRolled = true;
                        Main.sendRequest("update/dice " + Main.gameScreen.getDice1().getValoare()
                                + " " + Main.gameScreen.getDice2().getValoare());
                    }
                    break;
                default:
                    if (Main.wait) {
                        String response = Main.sendRequest("Game status/" + Main.indexPlayer);
                        manageResponse(response);
                    } else {
                        gameLogic();
                    }
            }
        }
    }

    private static void gameLogic() throws IOException, InterruptedException { // player current
        if (!Main.diceRolled) {
            return;
        }
        DrawUtil.init(Main.gameScreen, Main.gameScreen.getDice1().getValoare(),
                Main.gameScreen.getDice2().getValoare(), Main.indexPlayer);

        if (Main.triangleClicked != -1) {
            if (Main.indexPlayer == 0) {
                processing.image(Main.gameScreen.getBoard().getTriangles().get(0).getImgPieceWhite(),
                        processing.mouseX - 35, processing.mouseY - 35, 70, 70);
            } else {
                processing.image(Main.gameScreen.getBoard().getTriangles().get(0).getImgPieceBlack(),
                        processing.mouseX - 35, processing.mouseY - 35, 70, 70);
            }
        }
        if (DrawUtil.aiPiesaMancata()) {
            if (DrawUtil.aiLocSaPuiPiesaMancata()) {
                if (DrawUtil.selecteazaPiesaMancata()) {
                    ;
//                    DrawUtil.punePiesaMancata();
                }
            } else {
                DrawUtil.schimbaJucator();
            }
        } else { // nu ai piesa mancata
            if (Main.casaPlina) { // incepi sa scoti
                if (DrawUtil.aiZarBunPentruAScoate()) {
                    if (DrawUtil.selecteazaPiesa()) {
                        ;
//                        DrawUtil.scoatePiesa();
                    }
                } else {
                    if (DrawUtil.potiMutaPiesa()) {
                        if (DrawUtil.selecteazaPiesa()) {
                            ;
//                            DrawUtil.mutaPiesa();
                        }
                    } else {
                        DrawUtil.schimbaJucator();
                    }
                }
            } else {
                if (DrawUtil.potiMutaPiesa() /*&& Main.triangleClicked != -1*/) {
                    if (DrawUtil.selecteazaPiesa()) {
                        ;
//                        DrawUtil.mutaPiesa();
                    }
                } else {
                    DrawUtil.schimbaJucator();
                }
            }
        }
    }

    private static void manageResponse(String response) {
        if (!Main.playVsComputer) {
            if (response.equals("start")) {
                Main.wait = false;
                Main.diceRolled = false;
            } else if (response.startsWith("dice ")) {
                int valoare1 = Integer.parseInt(response.split(" ")[1]);
                int valoare2 = Integer.parseInt(response.split(" ")[2]);
                Main.gameScreen.getDice1().setValoare(valoare1);
                Main.gameScreen.getDice2().setValoare(valoare2);
                //System.out.println("update la zar");
            } else if (response.startsWith("move ")) {
                int position1 = Integer.parseInt(response.split(" ")[1]);
                int position2 = Integer.parseInt(response.split(" ")[2]);
                Main.gameScreen.getBoard().movePiece(position1, position2);
            } else if (response.startsWith("out ")) {
                int position = Integer.parseInt(response.split(" ")[1]);
                Main.gameScreen.getBoard().getTriangles().get(position).minusOnePiece();
            } else if (response.equals("over")) {
                Main.gameOver = true;
            }
        }
        if (response.endsWith("end")) {
            Main.wait = false;
            Main.diceRolled = false;
        }
    }

}
