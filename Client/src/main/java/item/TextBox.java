package item;

import processing.core.PApplet;

public class TextBox extends Item {
    private String text = "";

    public TextBox(PApplet processing, int x, int y, int width, int height) {
        super(processing, x, y, width, height);
    }

    @Override
    public void show() {
        processing.fill(255);
        processing.rect(x, y, width, height);

        processing.fill(0);
        processing.textSize(22);
        processing.text(text, x + 18, y + 32);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
