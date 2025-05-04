package com.coffee.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Window extends Canvas {

    @Serial
    private static final long serialVersionUID = 36752349087L;

    private final String name;
    private int Width, Height;
    private Thread thread;
    private JFrame frame;
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private int C_W, C_H;
    boolean oglEnabled = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration()
            .getImageCapabilities()
            .isAccelerated();


    public Window(String name, int w, int h) {
        this.Width = w;
        this.Height = h;
        this.name = name;
        initFrame();
        Mouse m = new Mouse();
        Keyboard k = new Keyboard();
        addMouseListener(m);
        addMouseMotionListener(m);
        addMouseWheelListener(m);
        addKeyListener(k);
    }

    public void initFrame(){
        this.frame = new JFrame(this.name);
        frame.add(this);
        frame.setUndecorated(false);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(Width, Height);
        setPreferredSize(dimension);
        frame.setMinimumSize(dimension);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        createBufferStrategy(3);
        Engine.Buffer = getBufferStrategy();
    }

    private void closeFrame() {
        frame.setVisible(false);
        frame.dispose();
    }

    //Getter's and Setter's

    public JFrame getFrame() {
        return this.frame;
    }

    public int getWidth() {
        Component c = frame.getComponent(0);
        return c.getWidth();
    }

    public int getHeight() {
        Component c = frame.getComponent(0);
        return c.getHeight();
    }

    public Dimension getScreenSize() {
        return toolkit.getScreenSize();
    }

}
