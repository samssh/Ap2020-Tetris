package model;

import hibernate.Connector;
import hibernate.SaveAble;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
public class Board implements SaveAble {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private static  Board board;
    @Column
    @Setter
    @Getter
    private int width = Constant.x[Constant.sizeNum], height = Constant.y[Constant.sizeNum];
    @Transient
    @Getter
    @Setter
    private Tile[][] tiles = new Tile[width][height];
    @ElementCollection
    @OrderColumn
    @Setter
    @Getter
    private int[][] tilesId = new int[width][height];
    @Column
    @Setter
    @Getter
    private int picNumber = 1;
    @OneToOne
    @Getter
    @Setter
    private Piece piece;

    public static Board getInstance() {
        return board;
    }

    public boolean canAddPiece(Piece p) {
        for (Tile t : p.getTiles()) {
            if (t.getX() < 0 || t.getX() >= tiles.length || t.getY() < 0 ||
                    t.getY() >= tiles[t.getX()].length) {
                return false;
            }
            if (tiles[t.getX()][t.getY()] != null) {
                return false;
            }

        }
        return true;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int endTurn() {
        turnPieceToTile();
        int cnt = 0;
        for (int i = height - 1; i >= 0; i--) {
            boolean flag = true;
            for (int j = 0; j < width; j++) {
                if (tiles[j][i] == null) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                i++;
                cnt++;
                for (int j = 0; j < width; j++) {
                    tiles[j][i] = null;
                }
                for (int k = i - 1; k >= 0; k--) {
                    for (int j = 0; j < width; j++) {
                        if (tiles[j][k] != null) {
                            tiles[j][k].setY(tiles[j][k].getY() + 1);
                            tiles[j][k + 1] = tiles[j][k];
                            tiles[j][k] = null;
                        }
                    }

                }
            }
        }
        return cnt;
    }

    private void turnPieceToTile() {
        for (Tile t : piece.getTiles())
            tiles[t.getX()][t.getY()] = t;
        piece = null;
    }

    @Override
    public void delete() {
        Connector connector = Connector.getConnector();
        piece.delete();
        connector.deleteArray(tiles);
        connector.delete(this);
    }

    @Override
    public void saveOrUpdate() {
        Connector connector = Connector.getConnector();
        piece.saveOrUpdate();
        tilesId=new int[tiles.length][tiles[0].length];
        connector.saveOrUpdateArray(tilesId, tiles);
        connector.saveOrUpdate(this);
    }

    @Override
    public void load() {
        Connector connector = Connector.getConnector();
        piece.load();
        tiles=new Tile[tilesId.length][tilesId[0].length];
        connector.fetchArray(Tile.class, tilesId, tiles);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Board clone() {
        Board board1 = new Board();
        board1.setHeight(this.height);
        board1.setPicNumber(this.picNumber);
        board1.setWidth(this.width);
        board1.tiles=new Tile[width][height];
        if (this.piece != null)
            board1.setPiece(this.piece.clone());
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.tiles[i][j]!=null)
                    board1.tiles[i][j]=this.tiles[i][j].clone();
            }
        }
        return board1;
    }
}
