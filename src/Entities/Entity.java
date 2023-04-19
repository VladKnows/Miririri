package Entities;

import java.awt.*;

public class Entity {
    int worldX, worldY;
    Rectangle solid;

    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    public Rectangle getSolid() { return solid; }

    public void setWorldX(int worldX) { this.worldX = worldX; }
    public void setWorldY(int worldY) { this.worldY = worldY; }
    public void setSolid(Rectangle solid) { this.solid = solid; }
}
