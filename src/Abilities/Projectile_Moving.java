package Abilities;

import Main.GameScreen;

import java.awt.*;
import java.io.IOException;

import static java.lang.Math.abs;

public class Projectile_Moving extends Projectile {
    public int toX, toY;
    public int speed;

    public Projectile_Moving(boolean friendly, boolean rotatable, boolean seeAfterHit, String name, int width, int height, int deviationX, int deviationY, int toX, int toY, int speed, boolean setOnce, Shape solid, int damage, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(friendly, rotatable, seeAfterHit, name, width, height, deviationX, deviationY, solid, damage, numberOfImages, durationsOfImages);

        this.setOnce = setOnce;
        this.speed = speed;
        this.toX = toX;
        this.toY = toY;
        stationary = false;
    }

    public Projectile_Moving(boolean friendly, boolean rotatable, boolean seeAfterHit, String name, int width, int height, int deviationX, int deviationY, int toX, int toY, int speed, boolean setOnce, Shape solid, int damage, int timeUntilNextHit, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(friendly, rotatable, seeAfterHit, name, width, height, deviationX, deviationY, solid, damage, timeUntilNextHit, numberOfImages, durationsOfImages);

        this.setOnce = setOnce;
        this.speed = speed;
        this.toX = toX;
        this.toY = toY;
        stationary = false;
    }

    @Override
    public void setTo(int toX, int toY) {
        if(!setOnce) {
            this.toX = toX;
            this.toY = toY;
        }
    }

    @Override
    public void setToOnce(int toX, int toY) {
        this.toX = toX;
        this.toY = toY;
    }

    public void followEntity() {
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

    @Override
    void setCoord(int x, int y) {
        if(!coordSet) {
            this.x = x - deviationX;
            this.y = y - deviationY;
            coordSet = true;
        }
    }

    @Override
    public void update(GameScreen gs, int entityX, int entityY) {
        setCoord(entityX, entityY);
        setTo(gs.getPlayer().getWorldX(), gs.getPlayer().getWorldY());
        followEntity();
        if(!friendly) {
            gs.getcChecker().CheckHit(gs.getPlayer(), this);
            if(itHitOne) {
                itHit = true;
                itHitOne = false;
            }
        }
        else {
            for(int i = 0; i < gs.enemies.length; i++) {
                if(gs.enemies[i] != null && gs.enemies[i].HP != 0)
                    gs.getcChecker().CheckHit(gs.enemies[i], this);
            }
            if(itHitOne) {
                itHit = true;
                itHitOne = false;
            }
        }
    }
}
