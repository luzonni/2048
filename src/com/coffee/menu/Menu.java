package com.coffee.menu;

import com.coffee.engine.Activity;
import com.coffee.engine.Engine;
import com.coffee.engine.FontG;
import com.coffee.engine.Keyboard;
import com.coffee.game.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Activity {

    private final Point title;
    private final Font fontTitle;
    private final List<Button> buttons;

    public Menu() {
        this.title = new Point();
        this.fontTitle = FontG.font(FontG.Rowdies, 150);
        int x = Engine.window.getWidth()/2;
        int y = Engine.window.getHeight()/3;
        this.buttons = new ArrayList<>();
        this.buttons.add(new Button("4x4", x, y, (b) -> {
            Engine.setActivity(new Game(4, 4));
        }));
        this.buttons.add(new Button("5x5", x, y + 100, (b) -> {
            Engine.setActivity(new Game(5, 5));
        }));
        this.buttons.add(new Button("3x5", x, y + 200, (b) -> {
            Engine.setActivity(new Game(3, 5));
        }));
        this.buttons.add(new Button("8x8", x, y + 300, (b) -> {
            Engine.setActivity(new Game(8, 8));
        }));
    }

    @Override
    public void tick() {
        int x = Engine.window.getWidth()/2;
        int y = Engine.window.getHeight()/6;
        this.title.setLocation(x, y);
        if(Keyboard.KeyPressed("SPACE")) {
            Engine.setActivity(new Game(5, 5));
        }
        for(Button button : buttons) {
            button.tick();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0xecc402));
        g.fillRect(0, 0, Engine.window.getWidth(), Engine.window.getHeight());
        renderTitle(g);
        for(Button button : buttons) {
            button.render(g);
        }
    }

    private void renderTitle(Graphics2D g) {
        String value = "2048";
        int wf = FontG.getWidth(value, fontTitle);
        int hf = FontG.getHeight(value, fontTitle);
        g.setFont(this.fontTitle);
        g.setColor(Color.white);
        g.drawString("2048", title.x - wf/2, title.y + hf/2);
    }

    @Override
    public void dispose() {

    }
}
