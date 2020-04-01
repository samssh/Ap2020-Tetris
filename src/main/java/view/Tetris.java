package view;

import model.Board;
import model.Piece;
import model.Player;
import model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static view.Images.*;

public class Tetris extends JPanel implements KeyListener {
    private static final Tetris tetris = new Tetris();

    public static Tetris getInstance() {
        return tetris;
    }

    private Tetris() {
        super();
        this.setSize(backSizeX, backSizeY);
        this.setLayout(null);
        this.addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        paintBoard(g);
        requestFocus();
    }

    private void paintBoard(Graphics g) {
        Board board = Board.getInstance();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                if (board.getTile(i, j) != null)
                    g.drawImage(tile[board.getTile(i, j).getColorNum()], i * tileSize,
                            j * tileSize, this);
            }
        }


        synchronized (board) {
            if (board.getPiece() != null) {
                for (Tile t : board.getPiece().getTiles()) {
                    g.drawImage(tile[t.getColorNum()], t.getX() * tileSize,
                            t.getY() * tileSize, this);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Board board = Board.getInstance();
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                synchronized (board) {
                    if (board.getPiece() != null) {
                        Piece p = board.getPiece();
                        board.setPiece(null);
                        p.rotate(1);
                        if (board.canAddPiece(p)) {
                            board.setPiece(p);
                        } else {
                            p.rotate(1);
                            board.setPiece(p);
                        }
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                synchronized (board) {
                    if (board.getPiece() != null) {
                        Piece p = board.getPiece();
                        board.setPiece(null);
                        p.shiftX(1);
                        if (board.canAddPiece(p)) {
                            board.setPiece(p);
                        } else {
                            p.shiftX(-1);
                            board.setPiece(p);
                        }
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                synchronized (board) {
                    if (board.getPiece() != null) {
                        Piece p = board.getPiece();
                        board.setPiece(null);
                        p.shiftX(-1);
                        if (board.canAddPiece(p)) {
                            board.setPiece(p);
                        } else {
                            p.shiftX(1);
                            board.setPiece(p);
                        }
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                synchronized (board) {
                    if (board.getPiece() != null) {
                        Piece p = board.getPiece();
                        board.setPiece(null);
                        while (board.canAddPiece(p)) p.shiftY(1);
                        Player player=Player.getInstance();
                        player.setScore(player.getScore() + 1);
                        p.shiftY(-1);
                        board.setPiece(p);
                        int t = board.endTurn();
                        player.setScore(t * 10 + player.getScore());
                        player.setScore(t * 10 + player.getScore());
                    }
                }
                break;
        }
        if ((keyEvent.getKeyCode()==KeyEvent.VK_Z)&&((keyEvent.getModifiers()& KeyEvent.CTRL_MASK)!=0)){
            synchronized (board) {
                if (board.getPiece() != null) {
                    board.getPiece().reset();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
