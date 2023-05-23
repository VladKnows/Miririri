package Objects;

import Entities.Entity;
import Main.GameScreen;
import Main.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class SuperObject {
    public static int markedForDeletion = 0;

    String name;
    BufferedImage image;
    int worldX, worldY;
    String message;

    public final static int OBJECT_ITEM = 0;
    public final static int OBJECT_POWERUP = 1;
    public final static int OBJECT_TELEPORTER = 2;

    public void draw(GameScreen gs, Graphics2D g2) {
        int screenX = worldX - gs.getPlayer().getWorldX() + gs.getPlayer().screenX;
        int screenY = worldY - gs.getPlayer().getWorldY() + gs.getPlayer().screenY;

        if(worldX + gs.tileSize> gs.getPlayer().getWorldX() - gs.getPlayer().screenX &&
                worldX - gs.tileSize< gs.getPlayer().getWorldX() + gs.getPlayer().screenX &&
                worldY + gs.tileSize> gs.getPlayer().getWorldY() - gs.getPlayer().screenY &&
                worldY - gs.tileSize< gs.getPlayer().getWorldY() + gs.getPlayer().screenY) {
            g2.drawImage(image, screenX, screenY, gs.tileSize, gs.tileSize, null);
        }
    }

    //Getters
    public BufferedImage getImage() { return  image; }
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    public String getName() { return name; }
    public String getMessage() { return message; }

    public void update() throws IOException {
        UI.loadMessage(message);
    }
}
