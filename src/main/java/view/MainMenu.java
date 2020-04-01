package view;


import controller.GameLoop;
import hibernate.Connector;
import model.*;
import model.NextPiece;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private static final MainMenu mainMenu = new MainMenu();
    private JPanel panel;
    private JButton newGame, continueGame, scoreBord, exit;
    private JLabel welcome;

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        return mainMenu;
    }

    public void start() {
        initializePanel();
        initializeButtons();
        initializeLabel();
    }

    private void initializePanel() {
        Tv tv = Tv.getInstance();
        panel = new JPanel();
        panel.setBounds(tv.getWidth() / 2 - 150, tv.getHeight() / 2 - 150, 300, 300);
        panel.setLayout(null);
        panel.setBackground(Color.blue);
        tv.getFrame().add(panel);
    }

    private void initializeButtons() {
        initializeNewGame();
        initializeContinue();
        initializeScoreBoard();
        initializeExit();
        JButton[] jButtons = {newGame, continueGame, scoreBord, exit};
        for (JButton jButton : jButtons) {
            jButton.setBackground(Color.red);
            jButton.setFocusPainted(false);
            jButton.setBorderPainted(false);
        }
    }

    private void initializeLabel() {
        welcome = new JLabel("welcome", SwingConstants.CENTER);
        welcome.setBounds(75, 10, 150, 30);
        welcome.setBackground(Color.red);
        welcome.setFocusable(false);
        panel.add(welcome);
    }

    private void initializeNewGame() {
        newGame = new JButton("new game");
        newGame.setBounds(75, 50, 150, 50);
        newGame.addActionListener(actionEvent -> {
            Tv.getInstance().getFrame().remove(panel);
            NewGameMenu.getInstance().start();
        });
        panel.add(newGame);
    }

    private void initializeContinue() {
        continueGame = new JButton("Continue game");
        continueGame.setBounds(75, 110, 150, 50);
        continueGame.addActionListener(actionEvent -> {
            Connector connector = Connector.getConnector();
            Board board = (Board) connector.fetchLast(Board.class);
            Player player = (Player) connector.fetchLast(Player.class);
            NextPiece nextPiece = (NextPiece) connector.fetchLast(NextPiece.class);

            if (board == null || player == null || nextPiece == null) {
                welcome.setText("no game to load");
                return;
            }
            Board.setBoard(board.clone());
            Player.setPlayer(player.clone());
            NextPiece.setNextPiece(nextPiece.clone());
            if (board.getWidth() == 15) {
                Constant.sizeNum = 0;
                Images.tileSize = 20;
            }
            if (board.getWidth() == 12) {
                Constant.sizeNum = 1;
                Images.tileSize = 25;
            }
            if (board.getWidth() == 10) {
                Constant.sizeNum = 2;
                Images.tileSize = 30;
            }
            Images.load();
            Piece.loadP();
            Tv.getInstance().getFrame().remove(panel);
            GameLoop.getInstance().start();
        });
        panel.add(continueGame);
    }

    private void initializeScoreBoard() {
        scoreBord = new JButton("score bord");
        scoreBord.setBounds(75, 170, 150, 50);
        scoreBord.addActionListener(actionEvent -> {
            Tv.getInstance().getFrame().remove(panel);
            ScoreBordMenu.getInstance().start();
        });
        panel.add(scoreBord);
    }

    private void initializeExit() {
        exit = new JButton("exit");
        exit.setBounds(75, 230, 150, 50);
        exit.addActionListener(actionEvent -> {
            System.exit(0);
        });
        panel.add(exit);
    }
}
