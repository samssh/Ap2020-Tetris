import hibernate.Connector;
import model.*;

public class k {
    public static void main(String[] args) {
        Connector connector=Connector.getConnector();
        connector.open();
        connector.beginTransaction();
        ScoreBord scoreBord=(ScoreBord)connector.fetchLast(ScoreBord.class);
        scoreBord.addRecord(new Record(90,"sam"));
        scoreBord.saveOrUpdate();

        System.out.println(connector.fetchAll(ScoreBord.class));
        System.out.println(connector.fetchAll(Record.class));
        connector.close();
        System.exit(0);
    }
}
