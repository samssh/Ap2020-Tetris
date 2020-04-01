package model;

import hibernate.Connector;
import hibernate.SaveAble;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Player implements SaveAble {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @Getter
    @Setter
    private String name;
    @Column
    @Setter
    @Getter
    private int score,deletedLines;
    @Setter
    private static Player player;

    public Player(String name, int score, int deletedLines) {
        this.name = name;
        this.score = score;
        this.deletedLines = deletedLines;
        player=this;
    }

    public static Player getInstance() {
        return player;
    }

    @Override
    public void delete() {
        Connector connector=Connector.getConnector();
        connector.delete(this);
    }

    @Override
    public void saveOrUpdate() {
//        id++;
        Connector connector=Connector.getConnector();
        connector.saveOrUpdate(this);
    }

    @Override
    public void load() {

    }

    @Override
    public Integer getId() {
        return id;
    }

    public Player clone(){
        return new Player(this.name,this.score,this.deletedLines);
    }
}
