package com.coffee.engine;

import com.coffee.game.Game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Engine implements Runnable {

    private Thread thread;
    private boolean isRunning;
    public static final double HZ = 60;
    public static final double T = 1_000_000_000.0;
    public static int FRAMES;
    public static int HERTZ;

    public static Window window;

    public Activity activity;

    public static BufferStrategy Buffer;

    public static Random rand;

    public Engine() {
        FontG.addFont("Inter");
        rand = new Random();
        window = new Window("2048", 800, 800);
        this.activity = new Game(3, 5);
        start();
    }

    public void start() {
        this.isRunning = true;
        this.thread = new Thread(this, "2048 - Engine");
        this.thread.start();
    }

    private Graphics2D getGraphics() {
        Graphics2D graphics = (Graphics2D) Buffer.getDrawGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, window.getWidth(), window.getHeight());
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        return graphics;
    }

    public void render(Graphics2D g) {
        g.dispose();
        Engine.Buffer.show();
    }

    @Override
    public void run() {
        long lastTimeHZ = System.nanoTime();
        double amountOfHz = Engine.HZ;
        double ns_HZ = Engine.T / amountOfHz;
        double delta_HZ = 0;
        long lastTimeFPS = System.nanoTime();
        double amountOfFPS = 60;
        double ns_FPS = Engine.T / amountOfFPS;
        double delta_FPS = 0;
        int Hz = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        window.requestFocus();
        while(isRunning) {
            try {
                long nowHZ = System.nanoTime();
                delta_HZ += (nowHZ - lastTimeHZ) / ns_HZ;
                lastTimeHZ = nowHZ;
                if(delta_HZ >= 1) {
                    activity.tick();
                    Hz++;
                    delta_HZ--;
                }

                long nowFPS = System.nanoTime();
                delta_FPS += (nowFPS - lastTimeFPS) / ns_FPS;
                lastTimeFPS = nowFPS;
                if(delta_FPS >= 1) {
                    Graphics2D g = getGraphics();
                    activity.render(g);
                    render(g);
                    frames++;
                    delta_FPS--;
                }

                //Show fps
                if(System.currentTimeMillis() - timer >= 1000){
                    Engine.FRAMES = frames;
                    frames = 0;
                    Engine.HERTZ = Hz;
                    Hz = 0;
                    timer += 1000;
                }
                Thread.sleep(1);
            }catch(Exception e) {
                System.out.println("ERROR!");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
