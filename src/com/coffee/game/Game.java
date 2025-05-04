package com.coffee.game;

import com.coffee.engine.Activity;
import com.coffee.engine.Engine;
import com.coffee.engine.Keyboard;
import com.coffee.menu.Menu;

import java.awt.*;

public class Game implements Activity {

    private final int width;
    private final int height;
    private Grid grid;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Grid(width, height);
    }

    @Override
    public void tick() {
        grid.tick();
        if(Keyboard.KeyPressed("ESCAPE")) {
            Engine.setActivity(new Menu());
        }
    }

    private void restart() {
        this.grid = new Grid(width, height);
    }

    private void gameOver() {

    }

    @Override
    public void render(Graphics2D g) {
        grid.render(g);
    }

    @Override
    public void dispose() {

    }
}
