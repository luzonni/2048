package com.coffee.game;

import com.coffee.engine.Engine;
import com.coffee.engine.Keyboard;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Grid {

    private Rectangle bounds;

    private final int width, height;
    private int boxSize;
    private final int padding;
    private final Slot[] grid;

    private boolean moved;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.padding = 10;
        this.boxSize = 80;
        this.bounds = new Rectangle(width*(boxSize+padding)+padding, height*(boxSize+padding)+padding);
        this.grid = buildGrid();
        spawn();
    }

    private Slot[] buildGrid() {
        Slot[] slots = new Slot[width*height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                slots[x+y*width] = new Slot(padding + (padding+boxSize)*x, padding + (padding+boxSize)*y, boxSize);
            }
        }
        return slots;
    }

    private void spawn() {
        List<Slot> emptySlots = new ArrayList<>();
        for(int i = 0; i < grid.length; i++) {
            if(grid[i].isEmpty()) {
                emptySlots.add(grid[i]);
            }
        }
        emptySlots.get(Engine.rand.nextInt(emptySlots.size())).putNew();
    }

    public Slot getSlot(int x, int y) {
        return grid[x+y*width];
    }

    private boolean allDone() {
        for(int i = 0 ; i < grid.length; i++) {
            Slot slot = grid[i];
            if(!slot.done()) {
                return false;
            }
        }
        return true;
    }

    private void setOnCenterScreen() {
    }

    public void tick() {
        setOnCenterScreen();
        for(Slot slot : grid) {
            if(slot != null)
                slot.tick();
        }
        if(allDone()) {
            if(Keyboard.KeyPressed("W") || Keyboard.KeyPressed("Up")) {
                moved = slide(TypeSlide.Up);
            }else if(Keyboard.KeyPressed("D") || Keyboard.KeyPressed("Right")) {
                moved = slide(TypeSlide.Right);
            }else if(Keyboard.KeyPressed("S") || Keyboard.KeyPressed("Down")) {
                moved = slide(TypeSlide.Down);
            }else if(Keyboard.KeyPressed("A") || Keyboard.KeyPressed("Left")) {
                moved = slide(TypeSlide.Left);
            }
            if(moved) {
                moved = false;
                spawn();
            }
        }
    }

    private boolean slide(TypeSlide slide) {
        int slides = 0;
        List<Slot> joined = new ArrayList<>();
        if(slide.equals(TypeSlide.Down)) {
            for(int y = height-2; y >= 0; y--) {
                for(int x = 0; x < width; x++) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Up)) {
            for(int y = 1; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Left)) {
            for(int y = 0; y < height; y++) {
                for(int x = 1; x < width; x++) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Right)) {
            for(int y = 0; y < height; y++) {
                for(int x = width - 2; x >= 0; x--) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }
        return slides > 0;
    }

    private int swap(List<Slot> joined, TypeSlide dir, int x, int y) {
        Slot curSlot = getSlot(x, y);
        if(curSlot.isEmpty())
            return 0;
        int nextX = x + dir.getNx();
        int nextY = y + dir.getNy();
        while((nextX >= 0 && nextX < width) && (nextY >= 0 && nextY < height)) {
            Slot nextSlot = getSlot(nextX, nextY);
            if(nextSlot.isEmpty()) {
                nextX += dir.getNx();
                nextY += dir.getNy();
            }else if(nextSlot.compareTo(curSlot) && !joined.contains(nextSlot)) {
                nextSlot.moveTo(curSlot);
                joined.add(nextSlot);
                return 1;
            }else {
                break;
            }
        }
        if((nextX-dir.getNx() != x || nextY-dir.getNy() != y) && getSlot(nextX-dir.getNx(), nextY-dir.getNy()).isEmpty()) {
            getSlot(nextX - dir.getNx(), nextY - dir.getNy()).moveTo(curSlot);
            return 1;
        }
        return 0;
    }

    public void render(Graphics2D g) {
        Graphics2D gg = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform();
        int x = (Engine.window.getWidth() - this.bounds.width)/2;
        int y = (Engine.window.getHeight() - this.bounds.height)/2;
        at.translate(x, y);
        gg.setTransform(at);
        gg.setColor(new Color(0xbeb0a3));
        gg.fillRoundRect(0, 0, this.bounds.width, this.bounds.height, (int)(this.bounds.width*0.05), (int)(this.bounds.height*0.05));
        for(Slot slot : grid) {
            slot.render(gg);
        }
        for(Slot slot : grid) {
            if(!slot.isEmpty())
                slot.content().render(gg);
        }
        gg.dispose();
    }

}
