package screen;

import processing.core.PApplet;

public class LoginScreen extends Screen{
    private boolean loggedIn;
    public LoginScreen(PApplet processing) {
        super(processing);
    }

    public void show() {
        hover = false;
        hoverButtons();
        selectCursor();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
