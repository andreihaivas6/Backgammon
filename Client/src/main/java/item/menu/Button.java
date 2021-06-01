package item.menu;

import item.Item;
import processing.Main;
import processing.core.PApplet;

public class Button extends Item {
    private Label label;

    public Button(PApplet processing, int x, int y, int width, int height, String name) {
        super(processing, x, y, width, height);
        this.label = new Label(processing, x, y, width, height, name);
    }

    @Override
    public void show() {
        if(isHover()) {
            processing.fill(Main.COLOR_BUTTON_PRESSED);
        } else {
            processing.fill(Main.COLOR_BUTTON);
        }
        processing.rect(x, y, width, height);
        label.show();
    }

    public boolean hasClicked() {
        if(processing.mousePressed && Main.mouseIsReleased && this.isHover()) {
            Main.mouseIsReleased = false;
            return true;
        }
        return false;
    }

    public boolean isHover() {
        return x <= processing.mouseX && processing.mouseX <= x + width
                && y <= processing.mouseY && processing.mouseY <= y + height;
    }

    public String event() {
        return label.getText();
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
