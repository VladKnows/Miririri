package Objects;

import Main.GameScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    String name;
    BufferedImage image;
    int worldX, worldY;
    String message;

    boolean collision = false;
    Rectangle solid;

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
    public boolean isCollision() { return collision; }
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    public Rectangle getSolid() { return solid; }
    public String getName() { return name; }
    public String getMessage() { return message; }
}
