package com.coffee.game;

import java.awt.*;

public class Slot {

    private final Rectangle bounds;
    private Box content;

    public Slot(int x, int y, int size) {
        this.bounds = new Rectangle(x, y, size, size);
    }

    public boolean isEmpty() {
        return this.content == null;
    }

    public boolean put(Box box) {
        boolean added = false;
        if(box.equals(content)) {
            box.plusValue(this.content.getValue());
            added = true;
        }
        this.content = box;
        return added;
    }

    public Box content() {
        return this.content;
    }

    public void putNew() {
        if(isEmpty()) {
            this.content = new Box(this.bounds.width);
            this.content.setPosition(bounds.x, bounds.y);
        }
    }

    public Box pop () {
        Box box = this.content;
        this.content = null;
        return box;
    }

    public boolean compareTo(Box box) {
        return !isEmpty() && this.content.equals(box);
    }

    public void tick() {
        if(!isEmpty()) {
            Point curP = this.content.getPosition();
            int fluid = 2;
            int nx = (bounds.x - curP.x)/fluid;
            int ny = (bounds.y - curP.y)/fluid;
            this.content.setPosition(curP.x + nx, curP.y + ny);
        }
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0xd1c3b6));
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 30, 30);
    }

}
