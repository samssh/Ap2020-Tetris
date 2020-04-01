package controller;

import lombok.SneakyThrows;

public abstract class Loop implements Runnable {
    private int fps;
    private long timer = System.currentTimeMillis();
    private boolean running = false;
    private Thread gameThread;

    public Loop(int fps) {
        this.fps = fps;
    }

    abstract void update();

    @SneakyThrows
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        int ns_per_update = 1000000000 / fps;
        timer = System.currentTimeMillis();
        double delta=0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime)*1.0 / ns_per_update;
            lastTime = now;
            if (delta<1){
                int milliseconds = (int) (ns_per_update*(1-delta)) / 1000000;
                int nanoseconds = (int) (ns_per_update*(1-delta)) % 1000000;
                Thread.sleep(milliseconds,nanoseconds);
            }
            while (running && delta >= 1){
                update();
                delta --;
            }
        }
    }

    public void start() {
        gameThread=new Thread(this);
        running = true;
        gameThread.start();
    }

    @SneakyThrows
    public void stop() {
        running = false;
        gameThread.join();
    }
}
