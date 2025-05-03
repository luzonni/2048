package com.coffee.game;

import com.coffee.engine.Activity;

import java.awt.*;

public class Game implements Activity {

    private int width;
    private final Grid grid;

    public Game(int size) {
        this.width = size;
        this.grid = new Grid(size, 4);
    }

    @Override
    public void tick() {
        grid.tick();
    }

    @Override
    public void render(Graphics2D g) {
        grid.render(g);
    }

    @Override
    public void dispose() {

    }
}
