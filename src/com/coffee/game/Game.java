package com.coffee.game;

import com.coffee.engine.Activity;
import com.coffee.engine.Keyboard;

import java.awt.*;

public class Game implements Activity {

    private int width;
    private int height;
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
            restart();
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
