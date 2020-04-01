package view;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class Status extends JPanel {
    private static final Status status = new Status();
    private JLabel name, score, deletedLine;

    public static Status getInstance() {
        return status;
    }


    private Status() {
        super();
        this.setBounds(300, 0, 150, 90);
        this.setFocusable(false);
        this.setLayout(null);
        this.setBackground(Color.BLUE);
        Player player = Player.getInstance();
        name = new JLabel("name: " + player.getName());
        name.setBounds(0, 0, 150, 30);
        score = new JLabel("score: " + player.getScore());
        score.setBounds(0, 30, 150, 30);
        deletedLine = new JLabel("deleted line: " + player.getDeletedLines());
        deletedLine.setBounds(0, 60, 150, 30);
        JLabel[] jLabels = {name, score, deletedLine};
        for (JLabel jLabel : jLabels) {
            this.add(jLabel);
            jLabel.setFocusable(false);
            jLabel.setBackground(Color.RED);
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        paintPlayer(graphics);
    }

    private void paintPlayer(Graphics g) {
        Player player = Player.getInstance();
        name.setText("name: " + player.getName());
        score.setText("score: " + player.getScore());
        deletedLine.setText("deleted line: " + player.getDeletedLines());
    }
}
