package view;

import model.Piece;
import model.Tile;
import model.Constant;

import javax.swing.*;
import java.awt.*;

import static view.Images.tile;
import static view.Images.tileSize;

public class NextPiece extends JPanel {
    private static final NextPiece nextPiece=new NextPiece();

    public static NextPiece getInstance() {
        return nextPiece;
    }

    private NextPiece(){
        super();
        this.setLayout(null);
        this.setBackground(Color.red);
        this.setBounds(300,366,4*30+30,2*30+30);
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        paintNext(graphics);
    }

    private void paintNext(Graphics g){
        Piece piece=model.NextPiece.getInstance().getNext();
        if (piece!=null){
            for (Tile t : piece.getTiles()) {
                g.drawImage(tile[t.getColorNum()],
                        20+(t.getX()- Constant.x[Constant.sizeNum]/2 +1) * tileSize,
                        t.getY() * tileSize +20, this);
            }
        }
    }
}
