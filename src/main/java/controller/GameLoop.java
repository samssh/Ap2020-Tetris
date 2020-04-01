package controller;

import hibernate.Connector;
import lombok.SneakyThrows;
import model.*;
import model.NextPiece;
import view.*;


public class GameLoop extends Loop {
    private static final GameLoop gameLoop = new GameLoop();
    private long lastShift = 0;

    private GameLoop() {
        super(60);
    }

    public static GameLoop getInstance() {
        return gameLoop;
    }

    @SneakyThrows
    @Override
    void update() {
        if (lastShift == 0) lastShift = System.currentTimeMillis();
        Board board = Board.getInstance();
        NextPiece nextPiece = NextPiece.getInstance();
        Player player = Player.getInstance();
        synchronized (board) {
            if (board.getPiece() == null) {
                if (board.canAddPiece(nextPiece.getNext())) {
                    Piece p = Piece.GetRandom();
                    board.setPiece(nextPiece.getNext());
                    nextPiece.setNext(p);
                    lastShift = System.currentTimeMillis();
                } else {
                    ScoreBord scoreBord = ScoreBord.getInstance();
                    scoreBord.addRecord(new Record(player.getScore(), player.getName()));
                    Connector connector = Connector.getConnector();
                    connector.beginTransaction();
                    scoreBord.saveOrUpdate();
                    connector.commit();
                    System.out.println(3);
//                    Thread.sleep(500);
                    new Thread(() -> {
                        removeComponent();
                        System.out.println(31);
                        MainMenu.getInstance().start();
                        System.out.println(4);
                    }).start();
                    GameLoop.getInstance().stop();
                    //lose state
                }
            } else {
                long now = System.currentTimeMillis();
                if (now - lastShift >= 250) {

                    Piece p = board.getPiece();
                    board.setPiece(null);
                    p.shiftY(1);
                    if (board.canAddPiece(p)) {
                        board.setPiece(p);
                        lastShift = now;
                    } else {
                        player.setScore(player.getScore() + 1);
                        p.shiftY(-1);
                        board.setPiece(p);
                        int t = board.endTurn();
                        player.setScore(t * 10 + player.getScore());
                        player.setScore(t * 10 + player.getScore());
                    }
                }
            }
        }
    }

    @Override
    public void start() {
        Tv tv = Tv.getInstance();
        tv.getFrame().add(GamePanel.getInstance());
        tv.getFrame().addKeyListener(Tetris.getInstance());
        super.start();
    }

    private void removeComponent() {

        Tv tv = Tv.getInstance();
        System.out.println(23);
        tv.getFrame().removeKeyListener(Tetris.getInstance());
        System.out.println("2");
        tv.getFrame().remove(GamePanel.getInstance());
        System.out.println("1");

    }

    @Override
    public void stop() {
        super.stop();
    }
}
