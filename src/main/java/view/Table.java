package view;

import model.ScoreBord;

import javax.swing.*;
import java.awt.*;

public class Table extends JPanel {
    private static final Table table = new Table();
    private int y = 25;

    public static Table getInstance() {
        return table;
    }

    private Table() {
        super();
        this.setBounds(300, 90, 300, 11 * y + 1);
        this.setLayout(null);
        this.setFocusable(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.pink);
        g.fillRect(0, 0, 300, 11 * y + 1);
        paintScoreBoard(g);
    }

    private void paintScoreBoard(Graphics g) {
        g.setColor(Color.green);
        g.drawLine(15, 0, 15, 11 * y);
        g.drawLine(200, 0, 200, 11 * y);
        for (int i = 0; i < 12; i++) {
            g.drawLine(0, i * y, 300, i * y);
        }
        g.setColor(Color.red);
        ScoreBord scoreBord = ScoreBord.getInstance();
        for (int i = 1; i < 11; i++) {
            g.drawString("" + (i), 0, (i + 1) * y-5);
        }
        g.drawString("name", 15+5, y-5);
        g.drawString("score", 200+5, y-5);
        g.setColor(Color.blue);
        for (int i = 1; i <= scoreBord.getRecords().size(); i++) {
            g.drawString(scoreBord.getRecords().get(i - 1).getName(), 15 +5, (i + 1) * y-5);
            g.drawString(scoreBord.getRecords().get(i - 1).getScore() + "", 200+5, (i + 1) * y-5);
        }
    }
}
