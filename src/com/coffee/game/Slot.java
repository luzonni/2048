package com.coffee.game;

import java.awt.*;

public class Slot {

    private final Rectangle bounds;
    private Box content;
    private final static double delta = 1d/60d;


    public Slot(int x, int y, int size) {
        this.bounds = new Rectangle(x, y, size, size);
    }

    public boolean isEmpty() {
        return this.content == null;
    }

    public void put(Box box) {
        if(box.equals(content)) {
            box.plusValue(this.content.getValue());
            box.setScale(1.25d);
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

    public void putNew(int level) {
        if(isEmpty()) {
            this.content = new Box(this.bounds.width);
            this.content.setValue((int)Math.pow(2, level));
            this.content.setPosition(bounds.x, bounds.y);
        }
    }

    public void moveTo(Slot slot) {
        this.put(slot.pop());
    }

    public boolean done() {
        if(isEmpty())
            return true;
        int x1 = this.bounds.x;
        int x2 = this.content.getBounds().x;
        int y1 = this.bounds.y;
        int y2 = this.content.getBounds().y;
        return x1 == x2 && y1 == y2;
    }

    public Box pop() {
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
            double fluid = 2;
            double nx = ((bounds.x - curP.x) / fluid);
            double ny = ((bounds.y - curP.y) / fluid);

            double x = Math.abs(nx) < 1f ? bounds.x : curP.x + nx;
            double y = Math.abs(ny) < 1f ? bounds.y : curP.y + ny;
            this.content.setPosition((int)x, (int)y);
            if(content.getScale() != 1d && done()) {
                double curScale = content().getScale();
                double def = ((1 - content.getScale())/2) * delta*20;
                if(def <= 0.1)
                    content.setScale(1);
                content.setScale(curScale + def);
            }
        }
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0xd1c3b6));
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, (int)(bounds.width*0.1), (int)(bounds.height*0.1));
    }

}
