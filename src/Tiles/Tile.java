package Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Main.GameScreen.scale;

public class Tile {
    public BufferedImage image;
    public boolean collision;
    public Rectangle solid;

    public Tile(BufferedImage im) {
        this.image = im;
    }

    public void setCollision(int x, int y, int width, int height) {
        collision = true;
        solid = new Rectangle(x * scale, y * scale, width * scale, height * scale);
    }
}
