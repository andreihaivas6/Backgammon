package screen;

import item.Button;
import item.Item;
import processing.Main;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen {
    protected final PApplet processing;
    protected final List<Item> items = new ArrayList<>();
    protected String buttonClicked = "";
    protected boolean hover = false;

    public Screen(PApplet processing) {
        this.processing = processing;
    }

    public abstract void show();

    public void addItem(Item item) {
        items.add(item);
    }

    public void hoverButtons() {
        for (Item item : items) {
            if(item instanceof Button && ((Button) item).getLabel().getText().equals("Press") &&
                    (Main.wait || Main.diceRolled)) {
                continue;
            }
            item.show();
            if (item instanceof Button && ((Button) item).isHover()) {
                hover = true;
                if (((Button) item).hasClicked()) {
                    buttonClicked = ((Button) item).getLabel().getText();
                }
            }
        }
    }

    public void selectCursor() {
        if (hover) {
            processing.cursor(PConstants.HAND);
        } else {
            processing.cursor(PConstants.ARROW);
        }
    }

    public String getClicked() {
        String result = new String(buttonClicked);
        buttonClicked = "";
        return result;
    }

    public PApplet getProcessing() {
        return processing;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(String buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }
}
