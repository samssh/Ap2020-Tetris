package view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;


public class Tv {

    private static Tv tv = new Tv();
    @Getter
    private JFrame frame = new JFrame("SAM Tetrisa");
    @Getter
    private int width=600,height=600;

    private Tv() {}

    public static Tv getInstance() {
        return tv;
    }

    public void initializeFrame() {
        frame.setUndecorated(true);
        frame.setSize(width, height);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(82, 243, 255));
        frame.setVisible(true);
        frame.getX();
    }

    public void update() {
        frame.repaint();
        frame.revalidate();
    }
}
