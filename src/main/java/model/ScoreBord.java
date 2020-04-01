package model;

import hibernate.Connector;
import hibernate.SaveAble;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class ScoreBord implements SaveAble {
    @Setter
    private static ScoreBord scoreBord;
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Transient
    @Setter
    @Getter
    private List<Record> records;
    @ElementCollection
    @Setter
    @Getter
    private List<Integer> recordsId;

    {
        records = new ArrayList<>();
        recordsId = new ArrayList<>();
    }

    public static ScoreBord getInstance() {
        return scoreBord;
    }

    public void addRecord(Record record) {
        records.add(record);
        records.sort(Record::compareTo);
        if (records.size() > Constant.recordListSize) {
            records.remove(records.size() - 1);
            System.out.println(134);
        }
    }


    @Override
    public void delete() {
        Connector connector = Connector.getConnector();
//        connector.deleteList(recordsId);
        connector.delete(this);
    }

    @Override
    public void saveOrUpdate() {
        Connector connector = Connector.getConnector();
        connector.saveOrUpdateList(recordsId,records);
        connector.saveOrUpdate(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load() {
        Connector connector = Connector.getConnector();
        setRecords(connector.fetchList(Record.class, recordsId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getId() {
        return id;
    }
}
