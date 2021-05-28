package screen;

import processing.core.PApplet;

public class MenuScreen extends Screen {
    public MenuScreen(PApplet processing) {
        super(processing);
    }

    public void show() {
        hover = false;
        hoverButtons();
        selectCursor();
    }
}
