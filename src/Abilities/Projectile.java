package Abilities;

import Main.GameScreen;
import Util.ImageVector;

import java.awt.*;
import java.io.IOException;

abstract public class Projectile {
    String name;
    public int x, y;
    int width, height;
    int deviationX, deviationY;
    public Shape solid;

    public int damage;
    public boolean itHit = false;

    public boolean oneTime = true;
    public int timerUntilNextHit = 0;
    public int timeUntilNextHit;

    public boolean coordSet;
    boolean stationary;
    boolean setOnce;

    public ImageVector image;

    protected Projectile(String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int numberOfImages, int []durationsOfImages) throws IOException {
        this.name = name;
        this.width = width;
        this.height = height;
        this.deviationX = deviationX;
        this.deviationY = deviationY;
        this.solid = solid;
        this.damage = damage;
        this.coordSet = false;

        image = new ImageVector("/Abilities", name, numberOfImages, durationsOfImages);
    }

    protected Projectile(String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int timeUntilNextHit, int numberOfImages, int []durationsOfImages) throws IOException {
        this(name, width, height, deviationX, deviationY, solid, damage, numberOfImages, durationsOfImages);

        this.oneTime = false;
        this.timeUntilNextHit = timeUntilNextHit;
    }

    abstract public void update(GameScreen gs, int entityX, int entityY);

    void setCoord(int x, int y) {
        if(!coordSet) {
            this.x = x - deviationX;
            this.y = y - deviationY;
            coordSet = true;
        }
    }

    public void draw(GameScreen gs, Graphics2D g2) {
        int screenX = x - gs.player.worldX + gs.player.screenX;
        int screenY = y - gs.player.worldY + gs.player.screenY;
        g2.drawImage(image.GetImage(), screenX, screenY, width, height, null);
    }
}
