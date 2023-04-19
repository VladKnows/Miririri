package Abilities;

import Main.GameScreen;

import java.awt.*;
import java.io.IOException;

import static java.lang.Math.abs;

public class Projectile_Moving extends Projectile {
    public int toX, toY;
    public int speed;

    public Projectile_Moving(String name, int width, int height, int deviationX, int deviationY, int toX, int toY, int speed, boolean setOnce, Shape solid, int damage, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(name, width, height, deviationX, deviationY, solid, damage, numberOfImages, durationsOfImages);

        this.setOnce = setOnce;
        this.speed = speed;
        this.toX = toX;
        this.toY = toY;
        stationary = false;
    }

    public Projectile_Moving(String name, int width, int height, int deviationX, int deviationY, int toX, int toY, int speed, boolean setOnce, Shape solid, int damage, int timeUntilNextHit, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(name, width, height, deviationX, deviationY, solid, damage, timeUntilNextHit, numberOfImages, durationsOfImages);

        this.setOnce = setOnce;
        this.speed = speed;
        this.toX = toX;
        this.toY = toY;
        stationary = false;
    }

    public void setTo(int toX, int toY) {
        if(!setOnce) {
            this.toX = toX;
            this.toY = toY;
        }
    }

    public void followPlayer() {
        if(!setOnce) {
            int distanceX, distanceY;
            distanceX = x - toX;
            distanceY = y - toY;

            if(abs(distanceY) > speed) {
                if (distanceY > 0)
                    y -= speed;
                else
                    y += speed;
            }
            if(abs(distanceX) > speed) {
                if (distanceX < 0)
                    x += speed;
                else
                    x -= speed;
            }
        }
    }

    @Override
    public void update(GameScreen gs, int entityX, int entityY) {
        setCoord(entityX, entityY);
        setTo(gs.getPlayer().getWorldX(), gs.getPlayer().getWorldY());
        followPlayer();
        gs.getcChecker().CheckHit(gs.getPlayer(), this);
    }
}
