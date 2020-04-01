package view;

import lombok.SneakyThrows;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private static MusicPlayer musicPlayer = new MusicPlayer();
    public static final int gameOver = 1, record = 2, row = 3;
    private File[] files = new File[]{new File("./src/main/resources/music/go0.wav"),
            new File("./src/main/resources/music/go1.wav"),
            new File("./src/main/resources/music/record.wav"),
            new File("./src/main/resources/music/row.wav"),
            new File("./src/main/resources/music/background.wav")};
    private Clip background = null;
    private boolean hasBackground = files[4].exists();

    public static MusicPlayer getInstance() {
        return musicPlayer;
    }


    public void startBackground() {
        if (hasBackground) {
            new Thread(() -> {
                if (background == null) {
                    try {
                        background = AudioSystem.getClip();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    try {
                        background.open(AudioSystem.getAudioInputStream(files[4].toURI().toURL()));
                    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                        e.printStackTrace();
                    }
                }
                background.loop(Clip.LOOP_CONTINUOUSLY);
            }).start();
        }
    }

    public void stopBackground() {
        if (hasBackground) {
            new Thread(() -> {
                background.stop();
            }).start();
        }
    }

    public void play(int i) {
        new Thread(() -> {
            File file = i == 1 ? files[(int) (Math.random() * 2)] : files[i];
            Clip music = null;
            try {
                music = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                music.open(AudioSystem.getAudioInputStream(file.toURI().toURL()));
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            music.start();
        }).start();
    }
}
