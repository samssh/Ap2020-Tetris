package controller;

import view.Tv;
public class GraphicLoop extends Loop {
    private static final GraphicLoop graphicLoop=new GraphicLoop();

    public static GraphicLoop getInstance() {
        return graphicLoop;
    }

    private GraphicLoop() {
        super(1000);
    }

    @Override
    void update() {
        Tv.getInstance().update();
    }

    @Override
    public void start() {
        Tv tv=Tv.getInstance();
        tv.initializeFrame();
        super.start();
    }
}
