package view;

import controller.GameLoop;
import hibernate.Connector;
import lombok.Getter;
import model.Board;
import model.Player;
import model.ScoreBord;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static GamePanel gamePanel=new GamePanel();
    @Getter
    private JButton exit, back, back1;

    public static GamePanel getInstance() {
        return gamePanel;
    }

    private GamePanel(){
        this.setBounds(0,0,600,600);
        this.setLayout(null);
        this.addKeyListener(Tetris.getInstance());
        initializeBack();
        initializeBack1();
        initializeExit();
        this.add(Tetris.getInstance());
        this.add(Table.getInstance());
        this.add(Status.getInstance());
        this.add(NextPiece.getInstance());
        this.add(exit);
        this.add(back);
        this.add(back1);
    }

    private void initializeExit() {
        exit = new JButton("save and exit");
        exit.setBounds(450, 0, 150, 90);
        exit.setFocusPainted(false);
        exit.setBorderPainted(false);
        exit.setBackground(Color.RED);
        exit.addActionListener(actionEvent -> {
            saveGame();
            Connector.getConnector().close();
            System.exit(0);
        });
    }

    private void initializeBack() {
        back = new JButton("save and back");
        back.setBounds(450, 366, 150, 45);
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setBackground(new Color(167, 27, 113));
        back.addActionListener(actionEvent -> {
            saveGame();
            Tv tv = Tv.getInstance();
            tv.getFrame().remove(GamePanel.getInstance());
            tv.getFrame().removeKeyListener(Tetris.getInstance());
            MusicPlayer.getInstance().stopBackground();
            GameLoop.getInstance().stop();
            MainMenu.getInstance().start();
        });
    }


    private void initializeBack1() {
        back1 = new JButton("back");
        back1.setBounds(450, 411, 150, 45);
        back1.setFocusPainted(false);
        back1.setBorderPainted(false);
        back1.setBackground(new Color(167, 56, 40));
        back1.addActionListener(actionEvent -> {
            Tv tv = Tv.getInstance();
            tv.getFrame().remove(GamePanel.getInstance());
            tv.getFrame().removeKeyListener(Tetris.getInstance());
            MusicPlayer.getInstance().stopBackground();
            GameLoop.getInstance().stop();
            MainMenu.getInstance().start();
        });
    }

    private void saveGame(){
        Connector connector = Connector.getConnector();
        connector.beginTransaction();
        connector.deleteAll();
        synchronized (Board.getInstance()) {
            Board.getInstance().saveOrUpdate();
        }
        model.NextPiece.getInstance().saveOrUpdate();
        Player.getInstance().saveOrUpdate();
        ScoreBord.getInstance().saveOrUpdate();
        connector.commit();
    }
}
