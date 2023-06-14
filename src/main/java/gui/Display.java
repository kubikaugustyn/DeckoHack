package gui;

import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame jFrame;
    private static Canvas canvas;
    private String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        initCanvas();
    }

    private void initCanvas() {
        jFrame = new JFrame(title);
        jFrame.setSize(width, height);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));

        jFrame.add(canvas);
        jFrame.pack();
    }

    public static Canvas getCanvas() {
        if (canvas == null) {
            System.out.println("Canvas is null");
            return null;
        }
        return canvas;
    }
}
