package view;

import javax.swing.*;
import java.awt.*;

public class ScoreBordMenu{
    private static ScoreBordMenu scoreBordMenu=new ScoreBordMenu();
    private JButton back;
    private JPanel panel;
    private Rectangle rectangle;

    public static ScoreBordMenu getInstance() {
        return scoreBordMenu;
    }

    private ScoreBordMenu(){}

    public void start(){
        initializePanel();
        initializeBack();
        Table table=Table.getInstance();
        rectangle=table.getBounds();
        table.setBounds(0,0,rectangle.width,rectangle.height);
        panel.add(table);
    }

    private void initializePanel(){
        Tv tv = Tv.getInstance();
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(tv.getWidth() / 2 - 150, tv.getHeight() / 2 - 150, 300, 300);
        tv.getFrame().add(panel);
        panel.setBackground(Color.blue);
    }

    private void initializeBack(){
        back=new JButton("back");
        back.setBounds(100,275,100,25);
        back.setBackground(Color.RED);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.addActionListener(actionEvent -> {
            panel.remove(back);
            Tv.getInstance().getFrame().remove(panel);
            Table.getInstance().setBounds(rectangle);
            MainMenu.getInstance().start();
        });
        panel.add(back);
    }
}
