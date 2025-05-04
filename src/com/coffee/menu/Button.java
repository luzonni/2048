package com.coffee.menu;

import com.coffee.engine.FontG;
import com.coffee.engine.Mouse;
import com.coffee.engine.Mouse_Button;

import java.awt.*;
import java.util.function.Consumer;

public class Button {

    private final Consumer<Button> action;
    private final Rectangle bounds;
    private final String value;
    private final int padding;
    private final Font font;
    private final Color color;

    public Button(String value, int x, int y, Consumer<Button> action) {
        this.action = action;
        padding = 20;
        this.value = value;
        this.color = new Color(0xf77c5a);
        this.font = FontG.font(FontG.Rowdies, 30);
        int wf = FontG.getWidth(value, font);
        int hf = FontG.getHeight(value, font);
        int width = padding*2 + wf;
        int height = padding*2 + hf;
        this.bounds = new Rectangle(x - width/2, y - height/2, width, height);
    }

    public void tick() {
        if(Mouse.clickOn(Mouse_Button.LEFT, this.bounds)) {
            action.accept(this);
        }
    }

    public void render(Graphics2D g) {
        int x = bounds.x;
        int y = bounds.y;
        g.setColor(color);
        g.fillRoundRect(x, y, bounds.width, bounds.height, (int)(bounds.width*0.1), (int)(bounds.height*0.1));
        g.setColor(Color.black);
        g.setFont(font);
        int hf = FontG.getHeight(value, font);
        g.drawString(value, x+ padding, y + padding + hf);
    }

}
