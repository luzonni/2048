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

    public void put(Box box) {
        if(box.equals(content)) {
            box.plusValue(this.content.getValue());
        }
        this.content = box;
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

    public boolean compareTo(Slot slot) {
        return !isEmpty() && this.content.equals(slot.content);
    }

    public void tick() {
        if(!isEmpty()) {
            Point curP = this.content.getPosition();
            float fluid = 2;
            float nx = (bounds.x - curP.x) / fluid;
            float ny = (bounds.y - curP.y) / fluid;

            float x = Math.abs(nx) < 1f ? bounds.x : curP.x + nx;
            float y = Math.abs(ny) < 1f ? bounds.y : curP.y + ny;
            this.content.setPosition((int)x, (int)y);
            if(content.getBounds().width != this.bounds.width) {
                float defaultSize = content.getBounds().width;
                float dif = (bounds.width - defaultSize)/4;
                float size = Math.abs(dif) < 1f ? bounds.width : defaultSize + dif;
                content.getBounds().setSize((int)size, (int)size);
            }
        }
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0xd1c3b6));
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 30, 30);
    }

}
