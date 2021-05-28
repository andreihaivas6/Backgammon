package processing;

import item.Button;
import item.Label;
import item.TextBox;
import processing.core.PApplet;
import screen.GameScreen;
import screen.MenuScreen;

public class Setup {
    public static void setup(PApplet processing) {
        Main.menuScreen = new MenuScreen(processing);
        Main.menuScreen.addItem(new Label(processing, 350, 60, 200, 100, "Backgammon"));
        Main.menuScreen.addItem(new Button(processing, 250,  300, 144, 50, "Join Room"));
        Main.menuScreen.addItem(new Button(processing, 515,  300, 170, 50, "Create Room"));
        Main.menuScreen.addItem(Main.info = new Label(processing, 380, 360, 200, 100, " "));
        Main.menuScreen.addItem(new Button(processing, 390,  420, 130, 50, "PvC Easy"));
        Main.menuScreen.addItem(new Button(processing, 390,  500, 130, 50, "PvC Hard"));
        Main.menuScreen.addItem(new Button(processing, 415,  580, 80, 50, "Exit"));
        Main.menuScreen.addItem(new Label(processing, 150, 120, 400, 100, "  If you have a room code, enter it and join the room.\n" +
                "If you want to create a new one, just type a valid code."));
        Main.menuScreen.addItem(new Label(processing, 300, 220, 200, 50, "Code: "));
        Main.menuScreen.addItem(new TextBox(processing, 380, 220, 200, 50));

        Main.gameScreen = new GameScreen(processing);
        Main.gameScreen.addItem(new Button(processing, 405,  660, 80, 50, "Quit"));
        Main.gameScreen.addItem(new Button(processing, 305,  325, 90, 50, "Press"));
    }
}
