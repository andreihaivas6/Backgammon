package processing;

import item.Label;
import processing.core.PApplet;
import screen.GameScreen;
import screen.MenuScreen;
import screen.Screen;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Thread.sleep;

// mvn package
// java -jar target/Client-1.0-SNAPSHOT.jar

public class Main extends PApplet {
    public static PApplet processing;
    static int WIDTH = 900, HEIGHT = 700;
    static final String SERVER_ADRESS = "127.0.0.1";
    static final int PORT = 8888;

    static boolean connected = false;
    public static Socket socket;
    public static PrintWriter out;
    public static BufferedReader in;
    public static String request = "";
    public static String response;

    public static boolean mouseIsPressed = false, mouseIsReleased = false, mouseIsHold = false;

    public static final int COLOR_BACKGROUND = new Color(245, 222, 179).getRGB();
    public static final int COLOR_BUTTON = Color.red.getRGB();
    public static final int COLOR_BUTTON_PRESSED = 255;

    public static GameScreen gameScreen;
    public static MenuScreen menuScreen;
    public static Label info;

    public static int currentScreen = 1, indexPlayer;
    public static boolean started = false, wait = true, diceRolled = false, casaPlina = false;
    public static boolean gameOver = false, winner = false;
    public static int triangleClicked = -1;
    
    public static boolean exampleHome = false;

    public static void main(String[] args) {
        PApplet.main("processing.Main", args);
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        Setup.setup(this);
    }

    public void draw() {
        try {
            if (!connected) {
                socket = new Socket(SERVER_ADRESS, PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                connected = true;
            }
            Draw.draw(this);
        } catch (Exception e) {
            System.err.println("No server listening... " + e);
        }
    }

    public static String sendRequest(String request) throws IOException, InterruptedException {
        System.out.println("Request: " + request);
        out.println(request);
        String response = in.readLine();
        System.out.println("Response: " + response);
//        sleep(500);
        return response;
    }

    public void keyPressed() {
        if (key == BACKSPACE && request.length() > 0) {
            request = request.substring(0, request.length() - 1);
        } else if ((key >= 'a' && key <= 'z') || (key >= 'A' && key <= 'Z') || (key >= '0' && key <= '9')) {
            request = request + key;
        }
    }

    public void mousePressed() {
        mouseIsPressed = true;
    }

    public void mouseReleased() {
        try {
            mouseIsReleased = true;
            if (triangleClicked != -1) {
                if (triangleClicked > 24 && DrawUtil.selecteazaPiesaMancata()) {
                    DrawUtil.punePiesaMancata();
                } else if (DrawUtil.selecteazaPiesa()) {
                    if(casaPlina && DrawUtil.aiZarBunPentruAScoate()) {
                        DrawUtil.scoatePiesa(this);
                    } else {
                        DrawUtil.mutaPiesa();
                    }
                }
                if (triangleClicked != -1) { // daca nu s-a modificat, s-a dat click in afara unui triunghi valid
                    gameScreen.getBoard().getTriangles().get(triangleClicked).plusOnePiece();
                    triangleClicked = -1;
                    gameScreen.clearRedTriangles();
                }
            }
        } catch (Exception ignored) {
        }
    }
}
