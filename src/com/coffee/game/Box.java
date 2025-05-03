package com.coffee.game;

import com.coffee.engine.FontG;

import java.awt.*;

public class Box {

    private int value;
    private static final Color[] colors = {
            new Color(0xFFF5CC),
            new Color(0xFFE0AA),
            new Color(0xFFB877),
            new Color(0xFF8F44),
            new Color(0xFF6611),
            new Color(0xFF3300),
            new Color(0xFF1900),
            new Color(0xFF0000),
            new Color(0xE00000),
            new Color(0xC00000),
            new Color(0xA00000),
            new Color(0x800000),
            new Color(0x600000),
            new Color(0x400000),
            new Color(0x200000),
            new Color(0x000000)
    };

    private final Rectangle bounds;

    public Box(int size) {
        this.bounds = new Rectangle(size/2, size/2);
        plusValue(2);
    }

    public void plusValue(int value) {
        this.value += value;
    }

    public int getValue() {
        return this.value;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        return this.value == ((Box)obj).value;
    }

    public Point getPosition() {
        return this.bounds.getLocation();
    }

    public void setPosition(int x, int y) {
        this.bounds.setLocation(x, y);
    }

    public void render(Graphics2D g) {
        int indexColor = (int)Math.sqrt(this.value)-1;
        if(indexColor >= colors.length)
            indexColor = colors.length-1;
        g.setColor(colors[indexColor]);
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 30, 30);
        String value = String.valueOf(this.value);
        Font font = FontG.font(FontG.Inter, (float)(bounds.width/(value.length()+1)));
        int wf = FontG.getWidth(value, font);
        int hf = FontG.getHeight(value, font);
        g.setFont(font);
        g.setColor(colors[(colors.length-1)-indexColor]);
        g.drawString(value, bounds.x + bounds.width/2 - wf/2, bounds.y + bounds.height/2 + hf/2);
    }

}
