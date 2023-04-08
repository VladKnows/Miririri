package Objects;

import Main.GameScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public String name;
    public BufferedImage image;
    public int worldX, worldY;
    public boolean collision = false;
    public Rectangle solid;
    public String message;

    public void draw(Graphics2D g2, GameScreen gs) {
        int screenX = worldX - gs.player.worldX + gs.player.screenX;
        int screenY = worldY - gs.player.worldY + gs.player.screenY;

        if(worldX + gs.tileSize> gs.player.worldX - gs.player.screenX &&
                worldX - gs.tileSize< gs.player.worldX + gs.player.screenX &&
                worldY + gs.tileSize> gs.player.worldY - gs.player.screenY &&
                worldY - gs.tileSize< gs.player.worldY + gs.player.screenY) {
            g2.drawImage(image, screenX, screenY, gs.tileSize, gs.tileSize, null);
        }
    }
}
