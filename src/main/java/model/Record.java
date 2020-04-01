package model;

import hibernate.Connector;
import hibernate.SaveAble;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Record implements Comparable<Record>, SaveAble {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @Setter
    @Getter
    private int score;
    @Column
    @Setter
    @Getter
    private String name;

    public Record(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public int compareTo(Record record) {
        return Integer.compare(record.score, this.score);
    }
    @Override
    public void delete() {
        Connector connector=Connector.getConnector();
        connector.delete(this);
    }

    @Override
    public void saveOrUpdate() {
        Connector connector=Connector.getConnector();
        connector.saveOrUpdate(this);
    }

    @Override
    public void load() {}

    @SuppressWarnings("unchecked")
    @Override
    public Integer getId() {
        return id;
    }
}
