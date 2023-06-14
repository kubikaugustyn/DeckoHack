package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import gui.Display;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class MapCanvas implements Runnable {
    private Display display;
    private Thread t;
    private boolean running;
    public int width, height;
    private String title;
    private BufferStrategy bs;
    private Graphics g;
    private BufferedImage testImage;

    public MapCanvas(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        init();
        while (running) {
            tick();
            render();
        }
        // stop()
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            System.out.println("bs is null....");
            Display.getCanvas().createBufferStrategy(3);
            return;
        }

        g=display.g
    }
}
