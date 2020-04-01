package model;

import hibernate.Connector;
import hibernate.SaveAble;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Entity
public class Tile implements SaveAble {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @Getter
    @Setter
    private int x, y, colorNum;


    public Tile(int x, int y, int colorNum) {
        this.x = x;
        this.y = y;
        this.colorNum = colorNum;
    }

    void rotate(double x, double y, int i) {
        double x1 = this.x - x + 0.5;
        double y1 = this.y - y + 0.5;
        if (i == 1) {
            double temp = -1 * x1;
            x1 = y1;
            y1 = temp;
        }
        if (i == -1) {
            double temp = -1 * y1;
            y1 = x1;
            x1 = temp;
        }
        this.x = (int) (x1 + x - 0.5);
        this.y = (int) (y1 + y - 0.5);
    }

    public Tile clone() {
        return new Tile(this.x, this.y, this.colorNum);
    }


    @Override
    public void delete() {
        Connector connector = Connector.getConnector();
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
