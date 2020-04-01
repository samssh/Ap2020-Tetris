package view;

import controller.GameLoop;
import hibernate.Connector;
import lombok.Getter;
import model.*;
import model.NextPiece;

import javax.swing.*;
import java.awt.*;

public class NewGameMenu {
    private static final NewGameMenu newGameMenu = new NewGameMenu();
    private JPanel panel;
    private String s = "no name";
    private JLabel name, size;
    private JTextField getName;
    private JRadioButton small, medium, big;
    private ButtonGroup buttonGroup;
    private JButton start, back;


    public static NewGameMenu getInstance() {
        return newGameMenu;
    }

    public void start() {
        initializePanel();
        initializeLabel();
        initializeButtons();
        initializeRadioB();
        initializeTextField();
    }

    private void initializePanel(){
        panel = new JPanel();
        Tv tv = Tv.getInstance();
        tv.getFrame().add(panel);
        panel.setBounds(tv.getWidth() / 2 - 150, tv.getHeight() / 2 - 150, 300, 300);
        panel.setLayout(null);
        panel.setBackground(Color.BLUE);
    }

    private void initializeLabel(){
        initializeName();
        initializeSize();
    }
    private void initializeName(){
        name = new JLabel("enter your name:");
        name.setFocusable(false);
        name.setBounds(10, 10, 150, 30);
        name.setBackground(Color.red);
        panel.add(name);
    }

    private void initializeSize(){
        size = new JLabel("select board size");
        size.setFocusable(false);
        size.setBounds(10, 100, 150, 30);
        size.setBackground(Color.red);
        panel.add(size);
    }

    private void initializeRadioB(){
        small = new JRadioButton("small", true);
        medium = new JRadioButton("medium", false);
        big = new JRadioButton("big", false);
        buttonGroup = new ButtonGroup();
        JRadioButton[] a = {small, medium, big};
        for (int i = 0; i < a.length; i++) {
            a[i].setBounds(10, 130 + i * 30, 150, 30);
            a[i].setBackground(Color.BLUE);
            a[i].setBorderPainted(false);
            a[i].setFocusPainted(false);
            buttonGroup.add(a[i]);
        }
        panel.add(small);
        panel.add(medium);
        panel.add(big);
    }

    private void initializeTextField(){
        getName = new JTextField("enter your name");
        getName.setBounds(10, 40, 150, 30);
        getName.addActionListener(actionEvent -> {
            s = getName.getText();
        });
        panel.add(getName);
    }

    private void initializeButtons(){
        initializeBack();
        initializeStart();
        JButton[] b = {start, back};
        for (JButton jButton : b) {
            jButton.setBackground(Color.red);
            jButton.setBorderPainted(false);
            jButton.setFocusPainted(false);
        }
    }

    private void initializeBack(){
        back = new JButton("back");
        back.setBounds(10, 240, 135, 30);
        back.addActionListener(actionEvent -> {
            Tv.getInstance().getFrame().remove(panel);
            s = "no name";
            MainMenu.getInstance().start();
        });
        panel.add(back);
    }

    private void initializeStart(){
        start = new JButton("start");
        start.setBounds(155, 240, 135, 30);
        start.addActionListener(actionEvent -> {
            Tv.getInstance().getFrame().remove(panel);
            Connector connector = Connector.getConnector();
            connector.beginTransaction();
            connector.deleteAll();
            connector.commit();
            if (small.isSelected()) {
                Constant.sizeNum = 2;
                Images.tileSize = 30;
            }
            if (medium.isSelected()) {
                Constant.sizeNum = 1;
                Images.tileSize = 25;
            }
            if (big.isSelected()) {
                Constant.sizeNum = 0;
                Images.tileSize = 20;
            }
            Images.load();
            Piece.loadP();
            Player.setPlayer(new Player(s, 0, 0));
            Board.setBoard(new Board());
            model.NextPiece.setNextPiece(new NextPiece());
            MusicPlayer.getInstance().startBackground();
            GameLoop.getInstance().start();
        });
        panel.add(start);
    }
}
