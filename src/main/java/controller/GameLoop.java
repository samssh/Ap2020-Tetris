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
        if (board.getPiece() == null) {
            if (board.canAddPiece(nextPiece.getNext())) {
                Piece p = Piece.GetRandom();
                board.setPiece(nextPiece.getNext());
                nextPiece.setNext(p);
                lastShift = System.currentTimeMillis();
            } else {
                ScoreBord scoreBord = ScoreBord.getInstance();
                if (scoreBord.addRecord(new Record(player.getScore(), player.getName()))) {
                    MusicPlayer.getInstance().play(MusicPlayer.record);
                } else {
                    MusicPlayer.getInstance().play(MusicPlayer.gameOver);
                }
                Connector connector = Connector.getConnector();
                connector.beginTransaction();
                connector.deleteAll();
                scoreBord.saveOrUpdate();
                connector.commit();
                Thread.sleep(500);
                removeComponent();
                MusicPlayer.getInstance().stopBackground();
                MainMenu.getInstance().start();
                GameLoop.getInstance().stop();
                //lose state
            }
        } else {
            long now = System.currentTimeMillis();
            if (now - lastShift >= 1000) {
                synchronized (board) {
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
                        System.out.println("we");
                        if (t > 0) MusicPlayer.getInstance().play(MusicPlayer.row);
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
        tv.getFrame().remove(GamePanel.getInstance());
        tv.getFrame().removeKeyListener(Tetris.getInstance());
    }

    @Override
    public void stop() {
        super.stop();
    }
}
