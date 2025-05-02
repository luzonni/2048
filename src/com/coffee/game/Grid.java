package com.coffee.game;

import com.coffee.engine.Engine;
import com.coffee.engine.Keyboard;

import java.awt.*;

public class Grid {

    private final int size;
    private final int padding;
    private final Slot[] grid;

    private TypeSlide moving = TypeSlide.Idle;

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
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                if(getSlot(x, y).isEmpty() && Engine.rand.nextInt(100) < 10) {
                    getSlot(x, y).putNew();
                }
            }
        }
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
        if(Keyboard.KeyPressing("Space")) {
            this.moving = slide(TypeSlide.values()[1+Engine.rand.nextInt(4)]);
            pressed = true;
        }
        if(Keyboard.KeyPressed("W") || Keyboard.KeyPressed("Up") || moving.equals(TypeSlide.Up)) {
            this.moving = slide(TypeSlide.Up);
            pressed = true;
        }else if(Keyboard.KeyPressed("D") || Keyboard.KeyPressed("Right")|| moving.equals(TypeSlide.Right)) {
            this.moving = slide(TypeSlide.Right);
            pressed = true;
        }else if(Keyboard.KeyPressed("S") || Keyboard.KeyPressed("Down")|| moving.equals(TypeSlide.Down)) {
            this.moving = slide(TypeSlide.Down);
            pressed = true;
        }else if(Keyboard.KeyPressed("A") || Keyboard.KeyPressed("Left")|| moving.equals(TypeSlide.Left)) {
            this.moving = slide(TypeSlide.Left);
            pressed = true;
        }
        if(pressed && this.moving.equals(TypeSlide.Idle)) {
            spawn();
        }
    }

    private TypeSlide slide(TypeSlide slide) {
        TypeSlide moving = TypeSlide.Idle;
        if(slide.equals(TypeSlide.Down)) {
            for(int y = size - 2; y >= 0; y--) {
                for(int x = 0; x < size; x++) {
                    Slot curSlot = getSlot(x, y);
                    Slot nextSlot = getSlot(x, y + 1);
                    if(!curSlot.isEmpty()) {
                        if(nextSlot.isEmpty() || curSlot.compareTo(nextSlot.content())) {
                            if(!nextSlot.put(curSlot.pop()))
                                moving = TypeSlide.Down;
                        }
                    }
                }
            }
        }else if(slide.equals(TypeSlide.Up)) {
            for(int y = 1; y < size; y++) {
                for(int x = 0; x < size; x++) {
                    Slot curSlot = getSlot(x, y);
                    Slot nextSlot = getSlot(x, y - 1);
                    if(!curSlot.isEmpty() ) {
                        if(nextSlot.isEmpty() || curSlot.compareTo(nextSlot.content())) {
                            if(!nextSlot.put(curSlot.pop()))
                                moving = TypeSlide.Up;
                        }
                    }
                }
            }
        }else if(slide.equals(TypeSlide.Left)) {
            for(int y = 0; y < size; y++) {
                for(int x = 1; x < size; x++) {
                    Slot curSlot = getSlot(x, y);
                    Slot nextSlot = getSlot(x - 1, y);
                    if(!curSlot.isEmpty()) {
                        if(nextSlot.isEmpty() || curSlot.compareTo(nextSlot.content())) {
                            if(!nextSlot.put(curSlot.pop()))
                                moving = TypeSlide.Left;
                        }
                    }
                }
            }
        }else if(slide.equals(TypeSlide.Right)) {
            for(int y = 0; y < size; y++) {
                for(int x = size - 2; x >= 0; x--) {
                    Slot curSlot = getSlot(x, y);
                    Slot nextSlot = getSlot(x + 1, y);
                    if(!curSlot.isEmpty()) {
                        if(nextSlot.isEmpty() || curSlot.compareTo(nextSlot.content())) {
                            if(!nextSlot.put(curSlot.pop()))
                                moving = TypeSlide.Right;
                        }
                    }
                }
            }
        }
        return moving;
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
