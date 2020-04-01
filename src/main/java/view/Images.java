package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Images {
    public static Image[] tile=new Image[6];
    public static Image background;
    public static int tileSize;
    public static int backSizeX=300,backSizeY=600;
    public static void load() {
        try {
            background=ImageIO.read(new File("./src/main/resources/picture/background/b1.png"));
            for (int i = 0 ;i < 6; i++) {
                tile[i] = ImageIO.read(new File("./src/main/resources/picture/"+tileSize+"/"+(i+1)+".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}