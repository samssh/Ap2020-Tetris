package model;

import hibernate.Connector;
import hibernate.SaveAble;
import lombok.*;
import view.Images;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Entity
@ToString
@NoArgsConstructor
public class Piece implements SaveAble{
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @Getter
    @Setter
    private double x, y;
    @Transient
    @Getter
    @Setter
    private List<Tile> tiles;
    @ElementCollection
    @Getter
    @Setter
    private List<Integer> tilesId;
    @Column
    @Setter
    @Getter
    private double xFrz,yFrz,rotates;
    {
        tilesId=new ArrayList<>();
        tiles=new ArrayList<>();
    }



    public static Piece GetRandom() {
        int index = (int) (Math.random() * pieceList.size());
        Piece p = pieceList.get(index).clone();
        p.changeColor((int) (Math.random() * Images.tile.length));
        return p;
    }

    private void changeColor(int colorNum) {
        tiles.forEach(t -> t.setColorNum(colorNum));
    }

    public Piece(double x, double y, Tile[] tiles) {
        this.x = x;
        this.y = y;
        this.tiles = Arrays.asList(tiles);
    }

    public Piece(double x, double y, List<Tile> tiles) {
        this.x = x;
        this.y = y;
        this.xFrz=x;
        this.yFrz=y;
        this.tiles = tiles;
    }

    public void shiftY(int i) {
        this.y = this.y + i;
        for (Tile t : tiles)
            t.setY(t.getY() + i);
    }

    public void shiftX(int i) {
        this.x = this.x + i;
        for (Tile t : tiles)
            t.setX(t.getX() + i);
    }

    public void rotate(int i) {
        rotates+=i;
        for (Tile t : tiles)
            t.rotate(x, y, i);
    }

    public void reset(){
        shiftX((int) (this.xFrz-this.x));
        shiftY((int) (this.yFrz-this.y));
        while (rotates!=0){
            rotate(-1);
        }
    }

    public Piece clone() {
        ArrayList<Tile> tiles1 = new ArrayList<>();
        for (Tile t : this.tiles) {
            tiles1.add(t.clone());
        }
        return new Piece(x, y, tiles1);
    }

    private static List<Piece> pieceList;

    public static void loadP(){
        pieceList = new ArrayList<>();
        int centerX = Constant.x[Constant.sizeNum] / 2, centerY = 0;
        Tile center = new Tile(centerX, centerY, 1);
        Tile t1 = new Tile(centerX + 1, centerY, 1);
        Tile t2 = new Tile(centerX + 2, centerY, 1);
        Tile t3 = new Tile(centerX - 1, centerY, 1);
        Tile t4 = new Tile(centerX, centerY + 1, 1);
        Tile t5 = new Tile(centerX + 1, centerY + 1, 1);
        Tile t6 = new Tile(centerX - 1, centerY + 1, 1);
        pieceList.add(new Piece(centerX + 1, centerY + 1, new Tile[]{center, t1, t2, t3}));
        pieceList.add(new Piece(centerX + 1, centerY + 1, new Tile[]{center, t1, t5, t3}));
        pieceList.add(new Piece(centerX, centerY + 1, new Tile[]{center, t1, t6, t3}));
        pieceList.add(new Piece(centerX + 1, centerY + 1, new Tile[]{center, t1, t5, t4}));
        pieceList.add(new Piece(centerX + 0.5, centerY + 1.5, new Tile[]{center, t4, t5, t6}));
        pieceList.add(new Piece(centerX, centerY + 1, new Tile[]{center, t1, t4, t6}));
        pieceList.add(new Piece(centerX, centerY + 1, new Tile[]{center, t5, t4, t3}));
    }

    @Override
    public void delete() {
        Connector connector=Connector.getConnector();
        connector.deleteList(tiles);
        connector.delete(this);
    }

    @Override
    public void saveOrUpdate() {
        Connector connector=Connector.getConnector();
        connector.saveOrUpdateList(tilesId,tiles);
        connector.saveOrUpdate(this);
    }
@SuppressWarnings("unchecked")
    @Override
    public void load() {
        Connector connector=Connector.getConnector();
        setTiles(connector.fetchList(Tile.class,tilesId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getId() {
        return id;
    }
}
