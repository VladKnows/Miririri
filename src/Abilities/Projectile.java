package Abilities;

import Main.GameScreen;
import Util.ImageVector;
import static Util.RotateImage.rotate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

abstract public class Projectile {
    boolean friendly;
    boolean rotatable;

    String name;
    public int x, y;
    int width, height;
    int deviationX, deviationY;
    public Shape solid;

    public int damage;
    public boolean itHit = false;
    public boolean itHitOne;

    public boolean oneTime = true;
    public boolean seeAfterHit = true;
    public int timerUntilNextHit = 0;
    public int timeUntilNextHit;

    public boolean coordSet;
    boolean stationary;
    boolean setOnce;

    public ImageVector image;


    protected Projectile(boolean friendly, boolean rotatable, boolean seeAfterHit, String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int numberOfImages, int []durationsOfImages) throws IOException {
        this.friendly = friendly;
        this.rotatable = rotatable;
        this.seeAfterHit = seeAfterHit;
        this.name = name;
        this.width = width;
        this.height = height;
        this.deviationX = deviationX;
        this.deviationY = deviationY;
        this.solid = solid;
        this.damage = damage;
        this.coordSet = false;

        switch (name) {
            case "Ultra_Sound":
                image = new ImageVector("/Abilities", name, 288, numberOfImages, durationsOfImages);
                break;
            case "Player_Ability_3", "Tornado":
                image = new ImageVector("/Abilities", name, 96, numberOfImages, durationsOfImages);
                break;
            default:
                image = new ImageVector("/Abilities", name, numberOfImages, durationsOfImages);
                break;
        }
    }

    protected Projectile(boolean friendly, boolean rotatable, boolean seeAfterHit, String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int timeUntilNextHit, int numberOfImages, int []durationsOfImages) throws IOException {
        this(friendly, rotatable, seeAfterHit, name, width, height, deviationX, deviationY, solid, damage, numberOfImages, durationsOfImages);

        this.oneTime = false;
        this.timeUntilNextHit = timeUntilNextHit;
    }

    abstract public void update(GameScreen gs, int entityX, int entityY);

    void init(int posX, int posY) {
        setCoord(posX, posY);
    }

    abstract void setCoord(int x, int y);

    public void setTo(int toX, int toY) {

    }

    public void setToOnce(int toX, int toY) {

    }

    public void draw(GameScreen gs, Graphics2D g2) {
        if(!(oneTime && itHit && !seeAfterHit)) {
            int screenX = x - gs.getPlayer().getWorldX() + gs.getPlayer().screenX;
            int screenY = y - gs.getPlayer().getWorldY() + gs.getPlayer().screenY;
            int angle = switch (gs.getPlayer().directionAtCast) {
                case 0 -> 270;
                case 2 -> 90;
                case 3 -> 180;
                default -> 0;
            };
            if(rotatable) {
                g2.drawImage(rotate(image.GetImage(), angle), screenX, screenY, width, height, null);
            }
            else {
                g2.drawImage(image.GetImage(), screenX, screenY, width, height, null);
            }
        }
    }
}
