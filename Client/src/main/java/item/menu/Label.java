package item.menu;

import item.Item;
import processing.core.PApplet;

public class Label extends Item {
    private String text;

    @Override
    public void show() {
        processing.fill(0);
        if(text.equals("Backgammon") || text.equals("Login/Sign Up")) {
            processing.textSize(32);
        } else {
            processing.textSize(22);
        }
        processing.text(text, x + 18, y + 32);
    }

    public Label(PApplet processing, int x, int y, int width, int height, String text) {
        super(processing, x, y, width, height);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
