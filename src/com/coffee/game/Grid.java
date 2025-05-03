package com.coffee.game;

import com.coffee.engine.Engine;
import com.coffee.engine.Keyboard;

import java.awt.*;

public class Grid {

    private final int size;
    private final int padding;
    private final Slot[] grid;

    public Grid(int size, int padding) {
        this.size = size;
        this.padding = padding;
        this.grid = buildGrid();
        spawn();
    }

    private Slot[] buildGrid() {
        //TODO ajeitar logica da distancia dos paddings
        int boxSize = Engine.window.getWidth()/size - padding;
        Slot[] slots = new Slot[(int)Math.pow(size, 2)];
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                slots[x+y*size] = new Slot(padding/2 + (padding+boxSize)*x, padding/2 + (padding+boxSize)*y, boxSize);
            }
        }
        return slots;
    }

    private void spawn() {
        int x;
        int y;
        do {
            x = Engine.rand.nextInt(size);
            y = Engine.rand.nextInt(size);
        }while ((!getSlot(x, y).isEmpty()));
        getSlot(x, y).putNew();
    }

    public Slot getSlot(int x, int y) {
        return grid[x+y*size];
    }

    public void tick() {
        for(Slot slot : grid) {
            if(slot != null)
                slot.tick();
        }
        boolean pressed = false;
        if(Keyboard.KeyPressed("W") || Keyboard.KeyPressed("Up")) {
            pressed = slide(TypeSlide.Up);
        }else if(Keyboard.KeyPressed("D") || Keyboard.KeyPressed("Right")) {
            pressed = slide(TypeSlide.Right);
        }else if(Keyboard.KeyPressed("S") || Keyboard.KeyPressed("Down")) {
            pressed = slide(TypeSlide.Down);
        }else if(Keyboard.KeyPressed("A") || Keyboard.KeyPressed("Left")) {
            pressed =slide(TypeSlide.Left);
        }
        if(pressed) {
            spawn();
        }
    }

    private boolean slide(TypeSlide slide) {
        boolean slided = false;
        if(slide.equals(TypeSlide.Down)) {
            for(int y = size-2; y >= 0; y--) {
                for(int x = 0; x < size; x++) {
                    slided = swap(slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Up)) {
            for(int y = 1; y < size; y++) {
                for(int x = 0; x < size; x++) {
                    slided = swap(slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Left)) {
            for(int y = 0; y < size; y++) {
                for(int x = 1; x < size; x++) {
                    slided = swap(slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Right)) {
            for(int y = 0; y < size; y++) {
                for(int x = size - 2; x >= 0; x--) {
                    slided = swap(slide, x, y);
                }
            }
        }
        return slided;
    }

    private boolean swap(TypeSlide dir, int x, int y) {
        Slot curSlot = getSlot(x, y);
        if(curSlot.isEmpty())
            return true;
        int nextX = x + dir.getNx();
        int nextY = y + dir.getNy();
        while((nextX >= 0 && nextX < size) && (nextY >= 0 && nextY < size)) {
            if(getSlot(nextX, nextY).isEmpty()) {
                nextX += dir.getNx();
                nextY += dir.getNy();
            }else if(getSlot(nextX, nextY).compareTo(curSlot)) {
                getSlot(nextX, nextY).put(curSlot.pop());
                return true;
            }else {
                break;
            }
        }
        if((nextX-dir.getNx() != x || nextY-dir.getNy() != y) && getSlot(nextX-dir.getNx(), nextY-dir.getNy()).isEmpty()) {
            getSlot(nextX - dir.getNx(), nextY - dir.getNy()).put(curSlot.pop());
            return true;
        }
        return false;
    }

    public void render(Graphics2D g) {
        for(Slot slot : grid) {
                slot.render(g);
        }
        for(Slot slot : grid) {
            if(!slot.isEmpty())
                slot.content().render(g);
        }
    }

}
