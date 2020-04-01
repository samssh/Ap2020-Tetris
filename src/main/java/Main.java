import controller.GraphicLoop;
import hibernate.Connector;
import model.ScoreBord;
import view.MainMenu;

public class Main {
    public static void main(String[] args) {
        Connector connector = Connector.getConnector();
        connector.open();
        ScoreBord scoreBord = (ScoreBord) connector.fetchLast(ScoreBord.class);
        if (scoreBord != null) ScoreBord.setScoreBord(scoreBord);
        else ScoreBord.setScoreBord(new ScoreBord());
        GraphicLoop graphicLoop = GraphicLoop.getInstance();
        MainMenu mainMenu = MainMenu.getInstance();
        mainMenu.start();
        graphicLoop.start();
    }
}
