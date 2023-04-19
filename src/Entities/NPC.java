package Entities;

import Main.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static Main.GameScreen.scale;

public class NPC extends Entity {
    public String name;
    public String []dialogue;
    public int []dialogueDuration;
    public int onLine;
    public int toLine;
    public int maxLine;
    BufferedImage image;

    public NPC(int worldX, int worldY, int x, int y, int width, int height, String name, int toLine, int maxLine) throws IOException {
        this.worldX = worldX;
        this.worldY = worldY;
        solid = new Rectangle(x * scale, y * scale, width * scale, height * scale);

        this.name = name;
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Friendly_NPCs/" + this.name + ".png")));

        onLine = 0;
        this.toLine = toLine;
        this.maxLine = maxLine;
    }

    public void writeDialogue(String []dialogue, int []dialogueDuration) {
        this.dialogue = dialogue;
        this.dialogueDuration = dialogueDuration;
    }

    public void nextDialogue(int newStartLine, int toLine) {
        onLine = newStartLine;
        this.toLine = toLine;
    }

    public void draw(GameScreen gs, Graphics2D g2) {
        int screenX = worldX - gs.getPlayer().worldX + gs.getPlayer().screenX;
        int screenY = worldY - gs.getPlayer().worldY + gs.getPlayer().screenY;

        if(worldX + gs.tileSize> gs.getPlayer().worldX - gs.getPlayer().screenX &&
                worldX - gs.tileSize< gs.getPlayer().worldX + gs.getPlayer().screenX &&
                worldY + gs.tileSize> gs.getPlayer().worldY - gs.getPlayer().screenY &&
                worldY - gs.tileSize< gs.getPlayer().worldY + gs.getPlayer().screenY) {
            g2.drawImage(image, screenX, screenY, gs.tileSize, gs.tileSize, null);
        }
    }
}
