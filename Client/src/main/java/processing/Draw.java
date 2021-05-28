package processing;

import processing.core.PApplet;
import screen.GameScreen;

import java.io.IOException;

public class Draw {
    private static PApplet processing;

    public static void draw(PApplet processing) throws IOException, InterruptedException {
        Draw.processing = processing;
        processing.background(Main.COLOR_BACKGROUND);
        processing.line(0, 0, processing.width, 0);

        switch (Main.currentScreen) {
            case 1 -> menu();
            case 2 -> game();
        }
    }

    private static void menu() throws IOException {
        Main.menuScreen.show();
        processing.text(Main.request, 390, 250);

        String buttonClicked = Main.menuScreen.getClicked();
        if (!buttonClicked.equals("")) {
            String request = buttonClicked + "/" + Main.request;
            System.out.println(request);

            Main.out.println(request);
            Main.request = "";

            Main.response = Main.in.readLine();
            takeActionInMenu(Main.response);
        }
    }

    private static void takeActionInMenu(String response) {
        System.out.println("R: " + response);
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
                break;
            case "Joined Room":
                Main.currentScreen = 2;
                Main.started = true;
                Main.wait = true;
                Main.indexPlayer = 1;
                break;
//            default: // vs C
//                Main.currentScreen = 2;
//                Main.started = true;
//                Main.wait = false;
//                Main.indexPlayer = 0;
//                break;
        }
    }

    private static void game() throws IOException, InterruptedException {
        Main.gameScreen.show();

        switch (Main.gameScreen.getClicked()) {
            case "Quit" :
                Main.out.println("Quit");
                Main.request = "";
                Main.response = Main.in.readLine();
                Main.currentScreen = 1;
                Main.started = false;
                break;
            case "Press":
                if(!Main.diceRolled && !Main.wait) { //////////////////////////////////////////////////////////////////////// !!!Main.wait
                    ((GameScreen) Main.gameScreen).rollDices();
                    Main.diceRolled = true;
                    Main.sendRequest("update\\dice " + ((GameScreen) Main.gameScreen).getDice1().getValoare()
                            + " " + ((GameScreen) Main.gameScreen).getDice2().getValoare());
                }
                break;
            default:
                String response = Main.sendRequest("Game status");
                manageResponse(response);
                System.out.println("W: " + Main.wait);
                if (!Main.wait) {
                    gameLogic();
                }
        }
    }

    private static void gameLogic() throws IOException, InterruptedException { // player current
        if(!(Main.gameScreen instanceof GameScreen)) {
            return;
        }


    }

    private static void manageResponse(String response) {
        switch (response) {
            case "start":
                Main.wait = false;
                break;
            default: break;
        }
    }
}
