package model;

import hibernate.Connector;
import hibernate.SaveAble;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
public class NextPiece implements SaveAble {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private static NextPiece nextPiece;
    @OneToOne
    @Setter
    private Piece next;


//    private NextPiece(){
//        next=Piece.GetRandom();
//    }


    public Piece getNext() {
        if (next==null)
            return Piece.GetRandom();
        return next;
    }

    public static NextPiece getInstance() {
        if (nextPiece==null)
            nextPiece=new NextPiece();
        return nextPiece;
    }

    @Override
    public void delete() {
        Connector connector=Connector.getConnector();
        connector.delete(next);
        connector.delete(this);
    }

    @Override
    public void saveOrUpdate() {
//        id++;
        Connector connector=Connector.getConnector();
        next.saveOrUpdate();
        connector.saveOrUpdate(this);
    }

    @Override
    public void load() {
        next.load();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getId() {
        return id;
    }

    public NextPiece clone(){
        NextPiece nextPiece1=new NextPiece();
        nextPiece1.setNext(this.next.clone());
        return nextPiece1;
    }
}
